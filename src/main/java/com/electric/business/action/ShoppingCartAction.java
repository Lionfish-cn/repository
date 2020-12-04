package com.electric.business.action;

import com.electric.business.dao.CustomerMapper;
import com.electric.business.dao.ShoppingCartMapper;
import com.electric.business.entity.Customer;
import com.electric.business.entity.Goods;
import com.electric.business.entity.ShoppingCart;
import com.electric.business.service.base.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ShoppingCartAction {
    @Resource
    private CustomerMapper customerMapper;
    @Autowired
    private IBaseService baseService;

    @Resource
    private ShoppingCartMapper shoppingCartMapper;
    @RequestMapping("/addShopingCart")
    public void addShoppingCart(HttpServletRequest request,HttpServletResponse response, @ModelAttribute Goods goods){
        String id = request.getParameter("id");
        String num = request.getParameter("number");
        Customer c = (Customer)baseService.findByPrimaryKey(id);
        ShoppingCart sc = new ShoppingCart();
        sc.setAddUser(c);
        sc.setBuyNumber(Integer.parseInt(num));
        sc.setGoods(goods);
        baseService.save(sc);
    }
}
