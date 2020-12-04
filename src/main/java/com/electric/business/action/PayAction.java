package com.electric.business.action;

import com.electric.business.dao.PayMapper;
import com.electric.business.entity.Goods;
import com.electric.business.entity.GoodsOrder;
import com.electric.business.entity.Pay;
import com.electric.business.service.base.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class PayAction {
    @Autowired
    private IBaseService baseService;
    @RequestMapping("/addPay")
    public void addPay(@ModelAttribute Pay pay){
        baseService.save(pay);
    }
    @RequestMapping("/pay")
    public void pay(HttpServletRequest request, @ModelAttribute GoodsOrder goodsOrder){
    }
}
