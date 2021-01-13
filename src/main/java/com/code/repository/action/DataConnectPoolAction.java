package com.code.repository.action;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/pool")
public class DataConnectPoolAction {

    /**
     * 根据选择的连接池获取数据库中的所有表
     * @param request
     * @param response
     * @return
     */
    public String findTables(HttpServletRequest request, HttpServletResponse response){
        String poolid = request.getParameter("poolid");

        return null;
    }

}
