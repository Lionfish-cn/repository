package com.electric.business.action;

import com.alibaba.fastjson.JSONObject;
import com.electric.business.dao.CustomerMapper;
import com.electric.business.entity.Customer;
import com.electric.business.service.ICustomerService;
import com.electric.business.service.IVerifyCodeService;
import com.electric.business.util.JwtUtil;
import com.electric.business.util.RequestUtil;
import com.electric.business.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/customer")
public class CustomerAction {
    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IVerifyCodeService iVerifyCodeService;

    @Resource
    private CustomerMapper customerMapper;

    @RequestMapping("/register")
    public String register(HttpServletRequest request, @ModelAttribute Customer customer) {
        customer.setPassword(DigestUtils.md5DigestAsHex(customer.getPassword().getBytes()));
        return customerService.save(customer);
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        String u = request.getParameter("username");
        String p = request.getParameter("password");

        String md5p = DigestUtils.md5DigestAsHex(p.getBytes());
        String message = null;
        Customer c = (Customer) customerMapper.findByUsername(u);
        if (c != null) {
            String password = c.getPassword();
            if (md5p.equals(password)) {
                message = ResponseUtil.loginResponseOk("登录成功", JwtUtil.generateToken(u));
            } else {
                message = "登录失败，账号密码不匹配";
            }
        }
        return message;
    }

}
