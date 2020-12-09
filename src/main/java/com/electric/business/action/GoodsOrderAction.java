package com.electric.business.action;

import com.electric.business.service.IGoodsOrderService;
import com.electric.business.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.RollbackException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/order")
public class GoodsOrderAction {

    @Autowired
    private IGoodsOrderService goodsOrderService;

    @RequestMapping("/sc")
    public void payOrders(HttpServletRequest request) throws Exception {
        if(!request.getMethod().equals("POST"))
            return;
        try {
            goodsOrderService.payOrders(request);
        }catch (Exception e){
            throw new RollbackException(e);
        }
    }

    @RequestMapping("/goods")
    public void payOrder(HttpServletRequest request) throws Exception {
        if(!request.getMethod().equals("POST"))
            return;
        try {
            goodsOrderService.payOrder(request);
        }catch (Exception e){
            throw  new RollbackException(e);
        }
    }

    @RequestMapping("/cancel")
    public String cancelOrder(HttpServletRequest request){
        if(!request.getMethod().equals("POST"))
            return null;
        try {
            String id = request.getParameter("id");
            if(StringUtil.isNotNull(id)) {
                boolean flag = goodsOrderService.cancelOrder(id);
                if(flag){
                    return "success";
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return e.getStackTrace().toString();
        }
        return null;
    }

    @RequestMapping("/findOrders")
    public List findOrders(HttpServletRequest request){
        if(!request.getMethod().equals("POST"))
            return null;
        String id = request.getParameter("id");
        return goodsOrderService.findOrdersByUser(id);
    }


}
