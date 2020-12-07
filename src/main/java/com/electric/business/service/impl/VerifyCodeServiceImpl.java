package com.electric.business.service.impl;

import com.electric.business.entity.VerifyCodeBlackList;
import com.electric.business.jpa.LetterCodeBlackListRepository;
import com.electric.business.redis.util.RedisUtil;
import com.electric.business.service.IVerifyCodeService;
import com.electric.business.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
public class VerifyCodeServiceImpl extends BaseServiceImpl implements IVerifyCodeService {
    private BufferedImage bi = null;

    public Graphics2D getGraphics2D() {
        int width = bi.getWidth();
        int height = bi.getHeight();
        Graphics2D g2 = (Graphics2D) bi.getGraphics();
        g2.setColor(Color.white);
        g2.fillRect(0, 0, width, height);
        g2.setFont(new Font("consolas", Font.BOLD, 30));
        Random random = new Random();
        for (int i = 0; i < 10; i++) {//添加干扰线
            g2.setColor(getRandomColor());
            g2.drawLine(random.nextInt(width), random.nextInt(height), random.nextInt(width), random.nextInt(height));
        }

        for (int i = 0; i < 30; i++) {
            g2.setColor(getRandomColor());
            g2.fillRect(random.nextInt(width), random.nextInt(height), 1, 1);
            //填充干扰点
        }

        return g2;
    }

    public Color getRandomColor() {
        Random random = new Random();
        Color c = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        return c;
    }

    /**
     * 生成图片验证码
     * @param request
     * @param response
     */
    public void generateVerifyCode(HttpServletRequest request, HttpServletResponse response) {
        String opt = request.getParameter("opt");
        Random random = new Random();
        String str = "";
        Graphics2D g2 = getGraphics2D();
        if (opt.equals("simple")) {//简单验证码
            bi = new BufferedImage(180, 70, BufferedImage.TYPE_INT_RGB);
            String simpleText = "0123456789qwertyuiopasdfghjklzxcvbnm";
            int x = 10;
            for (int i = 0; i < 4; i++) {
                int dot = simpleText.length();
                char txt = simpleText.charAt(random.nextInt(dot));
                g2.setColor(getRandomColor());
                int degree = random.nextInt() % 30;
                g2.rotate(degree * Math.PI / 180, x, 45);
                g2.drawString(txt + "", x, 45);
                g2.rotate(-degree * Math.PI / 180, x, 45);
                x += 48;
                str += txt + "";
            }
        }

        try {
            response.setContentType("image/png");
            request.setAttribute("txt", str);
            ServletOutputStream os = response.getOutputStream();
            ImageIO.write(bi, "PNG", os);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private LetterCodeBlackListRepository letterCodeBlackListRepository;

    /**
     * 发送短信验证码
     * @param request
     * @param response
     */
    public void sendShortLetterCode(HttpServletRequest request, HttpServletResponse response) {
        String phone = request.getParameter("phone");
        VerifyCodeBlackList verifyCodeBlackList = new VerifyCodeBlackList();
        verifyCodeBlackList.setPhone(phone);
        Example example = Example.of(verifyCodeBlackList);
        Optional<VerifyCodeBlackList> optionals = letterCodeBlackListRepository.findOne(example);
        String message = "由于您存在恶意发送验证码，将禁止你使用本系统。";
        if(!optionals.isPresent()){//如果手机号不存在黑名单中，才允许发送验证码
            String code = "";//TODO 发送验证码接口，并返回验证码
            //防止恶意发送验证码
            Object n = RedisUtil.stringGetOperations("malicesend:" + phone, null, "");
            if (n == null)
                n = 5;
            Integer mn = Integer.parseInt(n.toString());
            message = "success";
            if (mn > 0) {
                Long t = RedisUtil.TTL("malicesend:" + phone);
                if(t==null)
                    t = 10*120000l;
                RedisUtil.stringSetOperations("malicesend:" + phone, mn - 1, t , "");
                RedisUtil.stringSetOperations("repeat:num:" + phone, 5, 120000l, "");//判断验证码错误次数，最多5次，每次验证失败-1
                RedisUtil.stringSetOperations("letter:code:" + phone, code, 120000l, "");//设置验证码2分钟超时
            } else {
                //20分钟内如果重复发送五次验证码，则判定为恶意发送验证码。
                message = "由于您存在恶意发送验证码的行为，将禁止你使用本系统。";
                VerifyCodeBlackList blackList = new VerifyCodeBlackList();
                blackList.setHappenTime(new Date());
                blackList.setPhone(phone);
                blackList.setReason(message);
                letterCodeBlackListRepository.save(blackList);
            }
        }
        try {
            PrintWriter out = response.getWriter();
            out.write(message);
            out.close();
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检验短信验证码
     * @param request
     * @param response
     */
    public void validateVerificationCode(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        String phone = request.getParameter("phone");

        String message = "";
        try {
            //设置若验证码验证错误次数较多，则设置的停止时间
            Object time = RedisUtil.stringGetOperations("unlimitsendtime:" + phone, null, "");
            Object verify = RedisUtil.stringGetOperations("letter:code:" + phone, null, "");
            Object o = RedisUtil.stringGetOperations("repeat:num:" + phone, null, "");
            if (o == null)//若没有重复次数，说明验证码已超时，默认为1
                o = "1";
            Integer num = Integer.parseInt(o.toString());
            if (time == null && num > 0) {//若没有恶意输入并且错误次数没有超过5次，则
                if (verify == null) {
                    if (code.equals(verify)) {
                        message = "success";
                    } else {
                        if (o != null) {
                            RedisUtil.stringSetOperations("repeat:num:" + phone, num - 1, null, "ex");
                        }
                        message = "验证码错误，请重新输入！";
                    }
                } else {
                    message = "验证码已失效，请重新发送验证码。";
                }
            } else {
                if (time == null)
                    time = "5";
                Integer t = Integer.parseInt(time.toString());
                RedisUtil.stringSetOperations("unlimitsendtime:" + phone, t * 2, t * 2 * 1000 * 60l, "");
                message = "由于你验证码错误次数超过5次！将停止你发送验证码：" + t * 2 + "分钟！剩余：" + RedisUtil.TTL("unlimitsendtime:" + phone) / (1000 * 60) + " 分钟";
            }

            PrintWriter out = response.getWriter();
            out.write(message);
            out.close();
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
