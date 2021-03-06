package com.amber.controller;

import com.amber.async.EventModel;
import com.amber.async.EventProducer;
import com.amber.async.EventType;
import com.amber.model.HostHolder;
import com.amber.model.User;
import com.amber.service.UserService;
import com.amber.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    EventProducer eventProducer;

    @RequestMapping(path = {"/reg"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String register(Model model, @RequestParam(value = "username") String username,
                           @RequestParam(value = "password") String password,
                           @RequestParam(value = "rememberme", defaultValue = "0") int rememberme, HttpServletResponse response) {

        try {
            Map<String, Object> map = userService.register(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if (rememberme > 0) {
                    cookie.setMaxAge(3600 * 24 * 5);
                }
                response.addCookie(cookie);

                return JsonUtil.getJSONString(0, "注册成功");
            } else {
                return JsonUtil.getJSONString(1, map);
            }
        } catch (Exception e) {
            logger.info("注册异常： " + e.getMessage());
            return JsonUtil.getJSONString(2, "注册异常");
        }
    }

    @RequestMapping(path = {"/login"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String login(Model model, @RequestParam(value = "username") String username,
                        @RequestParam(value = "password") String password,
                        @RequestParam(value = "rememberme", defaultValue = "0") int rememberme, HttpServletResponse httpServletResponse) {

        try {
            Map<String, Object> map = userService.login(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if (rememberme > 0) {
                    cookie.setMaxAge(3600 * 24 * 5);
                }
                httpServletResponse.addCookie(cookie);

                EventModel loginModel = new EventModel(EventType.LOGIN);
                User user = (User) map.get("user");

                loginModel.setActorId(user.getId());
                loginModel.setExt("username", user.getName());
                loginModel.setExt("email", "1130229445@qq.com");

                eventProducer.fireEvent(loginModel);

                return JsonUtil.getJSONString(0, "登录成功");
            } else {
                return JsonUtil.getJSONString(1, map);
            }
        } catch (Exception e) {
            logger.info("登录异常： " + e.getMessage());
            return JsonUtil.getJSONString(2, "登录异常");
        }
    }

    @RequestMapping(path = {"/logout"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/";
    }
}
