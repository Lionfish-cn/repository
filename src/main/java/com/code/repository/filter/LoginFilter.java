package com.code.repository.filter;


import com.code.repository.util.JwtUtil;
import com.code.repository.util.RequestUtil;
import com.code.repository.util.ResponseUtil;
import com.code.repository.util.StringUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            //跨域
//            response.setHeader("Access-Control-Allow-Origin", "*");
//            //跨域 Header
//            response.setHeader("Access-Control-Allow-Methods", "*");
//            response.setHeader("Access-Control-Allow-Headers", "Content-Type,XFILENAME,XFILECATEGORY,XFILESIZE,AUTHORIZATION");

            Map<String, Object> params = RequestUtil.getHeaders(request);
            Object token = params.get("Authorization");
            if (token == null)
                token = request.getHeader("Authorization");
            if (token == null)
                token = request.getParameter("Authorization");
            if (token != null && StringUtil.isNotNull(token.toString())) {
                Boolean flag = JwtUtil.verifyToken(token.toString());
                if (!flag) {
                    PrintWriter out = response.getWriter();
                    out.write(ResponseUtil.filterResponseError(token.toString()));
                    out.flush();
                    out.close();
                    return;
                }
                filterChain.doFilter(request, response);
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
