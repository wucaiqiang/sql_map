package com.wu.sqlmap.interceptor;

import com.wu.sqlmap.util.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class ContextInterceptor extends HandlerInterceptorAdapter {
    private Set<String> filterUrl = new HashSet<>();

    {
        //过滤掉prometheus拉取数据的接口
        filterUrl.add("/login");
        filterUrl.add("/error");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUrl = request.getRequestURI();
        if (filterUrl.contains(requestUrl)) {
            return true;
        }
        String ticket = CookieUtils.getCookie(request,"ticket");
        if (StringUtils.isNotBlank(ticket) && "test01".equals(ticket)) {
            return true;
        }

        throw new RuntimeException("ticket为空，未验权");
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }
}
