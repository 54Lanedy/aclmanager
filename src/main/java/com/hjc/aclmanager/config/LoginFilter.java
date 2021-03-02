package com.hjc.aclmanager.config;

import com.hjc.aclmanager.entity.SysUser;
import com.hjc.aclmanager.utils.MyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆过滤器
 * 过滤器LoginFilter，检查user的session有效性，若有效则将每次的请求的用户信息存入ThreadLocal中
 * Created by liyue
 * Time 2021/1/12 10:45
 */
@WebFilter(filterName = "urlFilter", urlPatterns = {"/sys/*","/main.page"})
public class LoginFilter implements Filter {

    @Value("${server.servlet.context-path}")
    public String RootPath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resq = (HttpServletResponse) response;
        String path = req.getRequestURI();//获取用户请求的uri
        SysUser sysUser = (SysUser) req.getSession().getAttribute("USER_SESSION");
        if (sysUser == null && !path.contains("login")) {
            resq.sendRedirect(RootPath + "/logout.page");
        }else {
            RequestHolder.add(sysUser);
            RequestHolder.add(req);
            filterChain.doFilter(request,response);
        }
    }

    @Override
    public void destroy() {

    }
}
