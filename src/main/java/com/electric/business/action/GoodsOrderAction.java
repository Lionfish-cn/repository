package com.electric.business.action;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.electric.business.constants.EnumsConstans;
import com.electric.business.entity.Customer;
import com.electric.business.entity.Goods;
import com.electric.business.entity.GoodsOrder;
import com.electric.business.entity.ShoppingCart;
import com.electric.business.redis.util.RedisUtil;
import com.electric.business.service.ICustomerService;
import com.electric.business.service.IGoodsOrderService;
import com.electric.business.service.IGoodsService;
import com.electric.business.service.IShoppingCartService;
import com.electric.business.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.RollbackException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
public class GoodsOrderAction {
    @Autowired
    private IGoodsOrderService goodsOrderService;
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private IShoppingCartService shoppingCartService;
    @Autowired
    private ICustomerService customerService;
    @RequestMapping("/sc")
    @Transactional(rollbackFor = RollbackException.class)
    public void payOrder(HttpServletRequest request) throws Exception {
        if(!request.getMethod().equals("POST"))
            return;
        try {
            String idxs = request.getParameter("ids");
            String[] ids = idxs.split(";");
            Double price = 0d;
            int number = 0;
            Customer customer = null;
            List<Goods> arrGoods = new ArrayList<>();
            List<String> arrShoppingCart = new ArrayList<>();
            JSONArray arrays = new JSONArray();
            for(String id : ids) {
                ShoppingCart shoppingCart = (ShoppingCart) shoppingCartService.findByPrimaryKey(id);
                int buyNum = shoppingCart.getBuyNumber();
                Goods goods = shoppingCart.getGoods();
                String sales = goods.getGoodsSales();
                Double prices = buyNum * goods.getGoodsPrice();//计算
                if(StringUtil.isNotNull(sales)){
                    Double d = Double.parseDouble(sales);
                    prices = prices * d;
                }
                price += prices;
                number += buyNum;
                customer = shoppingCart.getAddUser();
                arrGoods.add(goods);
                arrShoppingCart.add(shoppingCart.getId());
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("goodsid",goods.getId());
                jsonObject.put("buynum",buyNum);
                arrays.add(jsonObject);
            }

            GoodsOrder go = new GoodsOrder();
            go.setGoods(arrGoods);
            go.setGoodsBuyTime(new Date());
            go.setGoodsBuyUser(customer);
            go.setOrderStatus(EnumsConstans.UNPAID.ordinal());//1为未支付
            go.setBuyNumber(number);
            go.setGoodsRelateNumber(arrays.toJSONString());

            goodsOrderService.save(go);
            //提交订单后，将移除购物车对应的内容
            goodsOrderService.deleteAll(arrShoppingCart);
            //占用库存
            goodsService.deductGoodsStock(arrays);
        }catch (Exception e){
            throw new RollbackException(e);
        }
    }

    @RequestMapping("/goods")
    public String payOrder(HttpServletRequest request, @ModelAttribute Goods goods) throws Exception {
        if(!request.getMethod().equals("POST"))
            return null;
        try {
            String id = request.getParameter("userid");
            String num = request.getParameter("number");
            Customer c = (Customer)customerService.findByPrimaryKey(id);
            Integer n = Integer.parseInt(num);

            JSONArray array = JSONArray.parseArray("[{'goodsid':'"+goods.getId()+"','buynum':'"+n+"'}]");
            GoodsOrder go = new GoodsOrder();
            go.setGoods(Arrays.asList(goods));
            go.setGoodsBuyTime(new Date());
            go.setGoodsBuyUser(c);
            go.setOrderStatus(EnumsConstans.UNPAID.ordinal());//1为未支付
            go.setGoodsRelateNumber(array.toJSONString());
            go.setBuyNumber(n);
            go.setGoodsRelateNumber(array.toJSONString());

            String sales = goods.getGoodsSales();
            if(StringUtil.isNotNull(sales)){
                Double d = Double.parseDouble(sales);
                Double price = n * goods.getGoodsPrice();
                price = price * d;
                go.setBuyPrice(price);
            }

            //设置订单超时时间
            setOrderExpire(go.getId());
            //占用库存
            goodsService.deductGoodsStock(array);
            //添加订单
            goodsOrderService.save(go);
            return go.getId();
        }catch (Exception e){
            e.printStackTrace();
            return e.getStackTrace().toString();
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

    public void setOrderExpire(String id){
        String key = "order-expire:"+id;
        RedisUtil.expire(key,(1000*60*20l));//设置订单20分钟超时，删除后，自动删除订单
    }
}
