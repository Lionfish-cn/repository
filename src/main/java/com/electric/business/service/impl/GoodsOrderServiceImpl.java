package com.electric.business.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.electric.business.constants.EnumsConstans;
import com.electric.business.dao.GoodsOrderMapper;
import com.electric.business.entity.Customer;
import com.electric.business.entity.Goods;
import com.electric.business.entity.GoodsOrder;
import com.electric.business.entity.ShoppingCart;
import com.electric.business.redis.util.RedisUtil;
import com.electric.business.service.ICustomerService;
import com.electric.business.service.IGoodsOrderService;
import com.electric.business.service.IGoodsService;
import com.electric.business.service.IShoppingCartService;
import com.electric.business.service.base.BaseServiceImpl;
import com.electric.business.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.RollbackException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GoodsOrderServiceImpl extends BaseServiceImpl implements IGoodsOrderService {
    @Resource
    GoodsOrderMapper goodsOrderMapper;
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private IShoppingCartService shoppingCartService;
    @Autowired
    private ICustomerService customerService;

    @Override
    public List<GoodsOrder> findOrdersByUser(String id) {
        return goodsOrderMapper.findOrdersByUser(id);
    }

    /**
     * 若有异常就回滚数据
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = RollbackException.class)
    public Boolean cancelOrder(String id) throws Exception {
       try {
           if(StringUtil.isNotNull(id)){
               GoodsOrder goodsOrder = (GoodsOrder)this.findByPrimaryKey(id);
               goodsOrder.setOrderStatus(EnumsConstans.CANCEL.ordinal());//取消状态
               goodsOrder.setGoodsCancel(new Date());
               goodsService.addBackGoodsStock(JSONArray.parseArray(goodsOrder.getGoodsRelateNumber()));
               String oid = update(goodsOrder);
               if(StringUtil.isNull(oid)){
                   throw new RollbackException("订单取消失败");
               }
               return true;
           }
       }catch (Exception e){
           throw new RollbackException(e);
       }
        return false;
    }

    /**
     * 设置读已提交的隔离级别
     * @param request
     */
    @Override
    @Transactional(rollbackFor = RollbackException.class,isolation = Isolation.READ_COMMITTED)
    public void payOrders(HttpServletRequest request) {
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
            go.setGoodsBuyTime(new Date());
            go.setGoodsBuyUser(customer);
            go.setOrderStatus(EnumsConstans.UNPAID.ordinal());//1为未支付
            go.setBuyNumber(number);
            go.setGoodsRelateNumber(arrays.toJSONString());
            go.setBuyPrice(price);
            save(go);
            //提交订单后，将移除购物车对应的内容
            shoppingCartService.deleteAll(arrShoppingCart.toArray(new String[]{}));
            //占用库存
            goodsService.deductGoodsStock(arrays);
        }catch (Exception e){
            throw new RollbackException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = RollbackException.class,isolation = Isolation.READ_COMMITTED)
    public String payOrder(HttpServletRequest request) throws Exception {
        String id = request.getParameter("userid");
        String goodsid = request.getParameter("goodsid");
        String num = request.getParameter("number");
        Customer c = (Customer)customerService.findByPrimaryKey(id);
        Integer n = Integer.parseInt(num);

        Goods goods = (Goods)goodsService.findByPrimaryKey(goodsid);
        JSONArray array = JSONArray.parseArray("[{'goodsid':'"+goods.getId()+"','buynum':'"+n+"'}]");
        GoodsOrder go = new GoodsOrder();
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
        save(go);
        return null;
    }

    public void setOrderExpire(String id){
        String key = "order-expire:"+id;
        RedisUtil.expire(key,(1000*60*20l));//设置订单20分钟超时，删除后，自动删除订单
    }
}
