package com.electric.business.action;

import com.electric.business.entity.Customer;
import com.electric.business.entity.Goods;
import com.electric.business.entity.ShoppingCart;
import com.electric.business.service.IShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/goods")
public class ShoppingCartAction {
    @Autowired
    private IShoppingCartService shoppingCartService;

    @RequestMapping("/addShoppingCart")
    public void addShoppingCart(HttpServletRequest request, @ModelAttribute Goods goods){
      try {
          String id = request.getParameter("userid");
          String num = request.getParameter("paynumber");
          Customer c = (Customer)shoppingCartService.findByPrimaryKey(id);
          ShoppingCart sc = new ShoppingCart();
          sc.setAddUser(c);
          sc.setBuyNumber(Integer.parseInt(num));
          sc.setGoods(goods);
          shoppingCartService.save(sc);
      }catch (Exception e){
          e.printStackTrace();
      }
    }
}
