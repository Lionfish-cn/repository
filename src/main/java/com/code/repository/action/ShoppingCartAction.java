package com.code.repository.action;

import com.code.repository.entity.Customer;
import com.code.repository.entity.Goods;
import com.code.repository.entity.ShoppingCart;
import com.code.repository.service.ICustomerService;
import com.code.repository.service.IShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/shoppingCart")
public class ShoppingCartAction {
    @Autowired
    private IShoppingCartService shoppingCartService;
    @Autowired
    private ICustomerService customerService;
    @RequestMapping("/add")
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

    @RequestMapping("/find")
    public List findShoppingCart(HttpServletRequest request){
        try {
            String userid = request.getParameter("userid");
            return shoppingCartService.findCartsByUser(userid);
        }catch (Exception e){
            e.printStackTrace();
            return Arrays.asList(e.getStackTrace());
        }
    }

    @RequestMapping("/remove")
    public void removeShoppingCart(HttpServletRequest request){
        String idxs = request.getParameter("ids");
        shoppingCartService.deleteAll(idxs.split(";"));
    }

}
