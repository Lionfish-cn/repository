package com.code.repository.loader;

import com.code.repository.dao.*;
import com.code.repository.dao.base.BaseMapper;
import com.electric.business.dao.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class MapperInitiative {
    public static Map<String, BaseMapper> myBatisMapper = null;

    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private EnshrineMapper enshrineMapper;
    @Resource
    private GoodsAppraisalMapper goodsAppraisalMapper;
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private GoodsOrderMapper goodsOrderMapper;
    @Resource
    private  PayMapper payMapper;
    @Resource
    private  ShoppingCartMapper shoppingCartMapper;

    @PostConstruct
    public void init(){
        myBatisMapper = new HashMap<>();
        myBatisMapper.put("customer",customerMapper);
        myBatisMapper.put("enshrine",enshrineMapper);
        myBatisMapper.put("goodsAppraisal",goodsAppraisalMapper);
        myBatisMapper.put("goods",goodsMapper);
        myBatisMapper.put("goodsOrder",goodsOrderMapper);
        myBatisMapper.put("pay",payMapper);
        myBatisMapper.put("shoppingCart",shoppingCartMapper);
    }
}
