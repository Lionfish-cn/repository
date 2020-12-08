package com.electric.business.action;

import com.electric.business.entity.Customer;
import com.electric.business.entity.Goods;
import com.electric.business.entity.ShoppingCart;
import com.electric.business.service.ICustomerService;
import com.electric.business.service.IShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/goods")
public class ShoppingCartAction {
    @Autowired
    private IShoppingCartService shoppingCartService;
    @Autowired
    private ICustomerService customerService;
    @RequestMapping("/addShoppingCart")
    public String addShoppingCart(HttpServletRequest request, @ModelAttribute Goods goods){
      try {
          String id = request.getParameter("userid");
          String num = request.getParameter("paynumber");
          Customer c = (Customer)customerService.findByPrimaryKey(id);
          ShoppingCart sc = new ShoppingCart();
          sc.setAddUser(c);
          sc.setBuyNumber(Integer.parseInt(num));
          sc.setGoods(goods);
          return shoppingCartService.save(sc);
      }catch (Exception e){
          e.printStackTrace();
          return "发生异常："+e.getStackTrace();
      }
    }

    @RequestMapping("/findShoppingCart")
    public List findShoppingCart(HttpServletRequest request){
        try {
            String userid = request.getParameter("userid");
            return shoppingCartService.findCartsByUser(userid);
        }catch (Exception e){
            e.printStackTrace();
            return Arrays.asList(e.getStackTrace());
        }
    }


}
