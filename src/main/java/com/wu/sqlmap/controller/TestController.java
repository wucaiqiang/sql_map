package com.wu.sqlmap.controller;

import com.wu.sqlmap.dto.LoginDto;
import com.wu.sqlmap.model.UserModel;
import com.wu.sqlmap.service.TestServiceI;
import com.wu.sqlmap.util.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@Controller
public class TestController {

    @Autowired
    private TestServiceI testServiceI;

    @RequestMapping(value = "get", method = RequestMethod.GET)
    @ResponseBody
    public List<UserModel> get() {
        return testServiceI.getSafely("1");
    }
    @RequestMapping(value = "post", method = RequestMethod.POST)
    @ResponseBody
    public List<UserModel> post() {
        return testServiceI.getSafely("1");
    }

    @RequestMapping(value = "getInject", method = RequestMethod.GET)
    @ResponseBody
    public List<UserModel> getInject(String name) {
        return testServiceI.getInject(name);
    }

    @RequestMapping(value = "getSafely", method = RequestMethod.GET)
    @ResponseBody
    public List<UserModel> getSafely(String name) {
        return testServiceI.getSafely(name);
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    @ResponseBody
    public String login(HttpServletRequest request, HttpServletResponse response, LoginDto loginDto) {
        log.info("login..");
        if ("test01".equals(loginDto.getUserName())) {
            CookieUtils.addCookie(response, "ticket", "test01");
        } else if ("delete".equals(loginDto.getUserName())) {
            CookieUtils.deleteCookie(request, response, "ticket");
        }
        return "sucess";
    }

    @RequestMapping(value = "postInject", method = RequestMethod.POST)
    @ResponseBody
    public List<UserModel> postInject(String name) {
        return testServiceI.getInject(name);
    }

    @RequestMapping(value = "postJson", method = RequestMethod.POST)
    @ResponseBody
    public List<UserModel> postJson(@RequestBody LoginDto loginDto) {
        return testServiceI.getInject(loginDto.getUserName());
    }


}
