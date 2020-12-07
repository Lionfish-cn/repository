package com.electric.business.action;

import com.electric.business.entity.GoodsOrder;
import com.electric.business.entity.Pay;
import com.electric.business.service.IPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class PayAction {
    @Autowired
    private IPayService baseService;

    /**
     * 添加支付
     * @param pay
     */
    @RequestMapping("/addPay")
    public void addPay(@ModelAttribute Pay pay){
        baseService.save(pay);
    }

    /**
     *
     * @param request
     * @param goodsOrder
     */
    @RequestMapping("/pay")
    public void pay(HttpServletRequest request, @ModelAttribute GoodsOrder goodsOrder){
    }
}
