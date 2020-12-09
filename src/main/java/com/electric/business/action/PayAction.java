package com.electric.business.action;

import com.electric.business.entity.Customer;
import com.electric.business.entity.GoodsOrder;
import com.electric.business.entity.Pay;
import com.electric.business.entity.base.BaseEntity;
import com.electric.business.service.ICustomerService;
import com.electric.business.service.IPayService;
import com.electric.business.util.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pay")
public class PayAction {
    @Autowired
    private IPayService payService;

    @Autowired
    private ICustomerService customerService;

    /**
     * 添加支付
     *
     * @param pay
     */
    @RequestMapping("/addPay")
    public String addPay(HttpServletRequest request, @ModelAttribute Pay pay) {
        if (!request.getMethod().equals("POST"))
            return null;
        return payService.save(pay);
    }

    /**
     * @param request
     * @param goodsOrder
     */
    @PostMapping("/")
    public String pay(HttpServletRequest request, @ModelAttribute GoodsOrder goodsOrder) {
        if (!request.getMethod().equals("POST"))
            return null;
        String userid = goodsOrder.getGoodsBuyUser().getId();
        Pay pay = findPay(userid);
        String account = pay.getPayAccount();
        return "";
    }

    @RequestMapping("/pp")
    public Boolean validPayPassword(HttpServletRequest request) {
        String pp = request.getParameter("pp");
        String userid = request.getParameter("userid");
        Customer customer = (Customer) customerService.findByPrimaryKey(userid);
        String paypassword = customer.getPayPassword();
        String ppp = DigestUtils.md5DigestAsHex(pp.getBytes());
        if (ppp.equals(paypassword)) {
            return true;
        }
        return false;
    }

    public Pay findPay(String userid) {
        try {
            Map map = new HashMap<>();
            map.put("pay_user", userid);
            map.put("is_default", 1);
            List<BaseEntity> baseEntities = payService.findList(map);
            if (!ArrayUtil.isEmpty(baseEntities)) {
                return (Pay) baseEntities.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean validIsEnough(HttpServletRequest request){
        String t = request.getParameter("t");
        if("alipay".equals(t)){//如果是支付宝

        }else if("yl".equals(t)){//如果是银联

        }
        return false;
    }

}
