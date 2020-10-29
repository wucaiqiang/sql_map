package com.wu.sqlmap.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 操作客户端cookie类
 *
 * @author zhangmingxing
 */
public class CookieUtils {

    /**
     * 添加cookie
     *
     * @param response
     * @param name
     * @param value
     */
    public static void addCookie(HttpServletResponse response, String name, String value) {
        try {
            if (value == null) {
                value = "";
            }
            Cookie cookie = new Cookie(name.trim(), value.trim());
            cookie.setPath("/");


            cookie.setMaxAge(600);
            response.addCookie(cookie);
        } catch (Exception e) {
        }
    }

    /**
     * 获取cookie对象
     *
     * @param request
     * @param name
     * @return
     */
    public static String getCookie(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = readCookieMap(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie=cookieMap.get(name);
            return cookie.getValue();
        }
        return null;
    }

    /**
     * 将cookie封装到Map里面
     *
     * @param request
     * @return
     */
    private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }


    /**
     * 删除cookie
     *
     * @param name
     */
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        if (getCookie(request, name) != null) {
            Cookie cookie = new Cookie(name, null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }

}
