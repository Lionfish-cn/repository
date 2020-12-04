package com.electric.business.action;

import com.electric.business.dao.GoodsMapper;
import com.electric.business.entity.Goods;
import com.electric.business.service.IGoodsService;
import com.electric.business.service.base.IBaseService;
import com.electric.business.util.ParseUtil;
import com.electric.business.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/goods")
public class GoodsAction {
    @Autowired
    private IBaseService baseService;
    @RequestMapping("/showGoods")
    public List findList(HttpServletRequest request) throws Exception {
        if (!request.getMethod().equals("POST"))
            return null;
        try {
            String queryString = RequestUtil.getQueryString(request);
            Map<String,Object> params = ParseUtil.parseMapByQueryString(queryString);
            return baseService.find(new Goods(),params);
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
            id = baseService.save(goods);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "添加成功，id为：" + id;
    }
}
