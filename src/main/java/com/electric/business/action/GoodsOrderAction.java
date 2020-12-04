package com.electric.business.action;

import com.electric.business.dao.CustomerMapper;
import com.electric.business.dao.GoodsOrderMapper;
import com.electric.business.dao.ShoppingCartMapper;
import com.electric.business.entity.Customer;
import com.electric.business.entity.Goods;
import com.electric.business.entity.GoodsOrder;
import com.electric.business.entity.ShoppingCart;
import com.electric.business.redis.util.RedisUtil;
import com.electric.business.service.IGoodsService;
import com.electric.business.service.base.IBaseService;
import com.electric.business.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
public class GoodsOrderAction {
    @Autowired
    private IBaseService baseService;
    @Resource
    private GoodsOrderMapper goodsOrderMapper;
    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private IGoodsService goodsService;

    @RequestMapping("/scTopay")
    public void payOrder(HttpServletRequest request, HttpServletResponse response, @ModelAttribute ShoppingCart shoppingCart) throws Exception {
        //TODO 还没有完成多个订单整合的，或后端整合或前端整合
        int buyNum = shoppingCart.getBuyNumber();
        Goods goods = shoppingCart.getGoods();
        GoodsOrder go = new GoodsOrder();
        go.setGoods(shoppingCart.getGoods());
        go.setGoodsBuyTime(new Date());
        go.setGoodsBuyUser(shoppingCart.getAddUser());
        go.setOrderStatus("1");//1为未支付
        go.setBuyNumber(buyNum);
        String sales = goods.getGoodsSales();
        if(StringUtil.isNotNull(sales)){
            Double d = Double.parseDouble(sales);
            Double price = buyNum * goods.getGoodsPrice();
            price = price * d;
            go.setBuyPrice(price);
        }
        baseService.save(go);
        //提交订单后，将移除购物车对应的内容
        baseService.delete(shoppingCart);
        //占用库存
        goodsService.deductGoodsStock(goods,buyNum);
    }

    @RequestMapping("/goodsToPay")
    public String payOrder(HttpServletRequest request, @ModelAttribute Goods goods) {
        String id = request.getParameter("userid");
        String num = request.getParameter("number");
        Customer c = (Customer)customerMapper.findByPrimaryKey(id);
        Integer n = Integer.parseInt(num);

        GoodsOrder go = new GoodsOrder();
        go.setGoods(goods);
        go.setGoodsBuyTime(new Date());
        go.setGoodsBuyUser(c);
        go.setOrderStatus("1");//1为未支付
        go.setBuyNumber(n);
        String sales = goods.getGoodsSales();
        if(StringUtil.isNotNull(sales)){
            Double d = Double.parseDouble(sales);
            Double price = n * goods.getGoodsPrice();
            price = price * d;
            go.setBuyPrice(price);
        }
        setOrderExpire(go.getId());
        return go.getId();
    }

    public void setOrderExpire(String id){
        String key = "order-expire:"+id;
        RedisUtil.expire(key,(1000*60*20l));//设置订单20分钟超时，删除后，自动删除订单
    }
}
