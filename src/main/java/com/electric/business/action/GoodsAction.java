package com.electric.business.action;

import com.electric.business.entity.Goods;
import com.electric.business.entity.GoodsCategory;
import com.electric.business.service.IGoodsCategoryService;
import com.electric.business.service.IGoodsService;
import com.electric.business.util.ParseUtil;
import com.electric.business.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/goods")
public class GoodsAction {
    @Autowired
    private IGoodsService goodsService;
    @RequestMapping("/showGoods")
    public List findList(HttpServletRequest request) throws Exception {
        if (!request.getMethod().equals("POST"))
            return null;
        try {
            Map<String,Object> params = RequestUtil.getQueryString(request);
            return goodsService.findList(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/add")
    public String goodsAdd(HttpServletRequest request, HttpServletResponse response, @ModelAttribute Goods goods) throws Exception {
        if (!request.getMethod().equals("POST"))
            return "不允许使用GET方式添加数据！";
        String id = "";
        try {
            id = goodsService.save(goods);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }
    @Autowired
    private IGoodsCategoryService goodsCategoryService;

    @RequestMapping("/v")
    public Goods toGoods(HttpServletRequest request){
       try {
           String id = request.getParameter("categoryid");
           GoodsCategory gc = (GoodsCategory) goodsCategoryService.findByPrimaryKey(id);
           Goods goods = new Goods();
           goods.setCategoryType(gc);
           goods.setGoodsName(gc.getCategoryName());
           return goods;
       }catch (Exception e){
           e.printStackTrace();
       }
       return null;
    }
}
