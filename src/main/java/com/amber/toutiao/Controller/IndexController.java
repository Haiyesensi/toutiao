package com.amber.toutiao.Controller;


import com.amber.toutiao.Service.ToutiaoService;
import com.amber.toutiao.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;


@Controller
public class IndexController {
    private static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private ToutiaoService toutiaoService;

    @RequestMapping(path = {"/","/index"})
    @ResponseBody
    public String getString(HttpSession session){
        logger.info("Enter Index");

        return "home"+ session.getAttribute("userStatus")+"<br>"+
                " Say: "+toutiaoService.Say();
    }

    @RequestMapping(path = {"/profile/{groupId}/{name}"})
    @ResponseBody
    public String getProfile(@PathVariable("groupId") int groupId,
                             @PathVariable("name") String name,
                             @RequestParam(value = "key",defaultValue = "0") int key,
                             @RequestParam(value = "value",defaultValue = "0") int value){
        return String.format("groupId = {%d}, name = {%s}, key = {%d}, value = {%d}",groupId,name,key,value);
    }

    @RequestMapping(value = "/vm")
    public String home(Model model){
        model.addAttribute("value1","this is model's value");
        model.addAttribute("value3","this is model's value");

        User user = new User(01,"shihchang",20,"jjjjjjkk");
        model.addAttribute("user",user);

        List<String> list = Arrays.asList(new String[]{"as","ss","ccc"});
        model.addAttribute("list",list);

        Map<String,String>map = new HashMap<>();
        for(int i = 1;i <= 5;i++ ){
            map.put(String.valueOf(i),String.valueOf(i*i));
        }
        model.addAttribute("maptest",map);
        return "home";
    }

    @RequestMapping(value = "/request")
    @ResponseBody
    public String request(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, HttpSession httpSession){
        StringBuffer h = new StringBuffer();
        Enumeration<String>enumeration = httpServletRequest.getHeaderNames();
        int i = 1;
        while(enumeration.hasMoreElements()){
            String name = enumeration.nextElement();
            h.append(i+" "+name+"   "+httpServletRequest.getHeader(name)+"<br>");
            i++;
        }
        for(Cookie cookie:httpServletRequest.getCookies()){
            h.append(cookie.toString()+"<br>");
            h.append(cookie.getName()+"<br>");
            h.append(cookie.getComment());
            h.append(cookie.getDomain()+"<br>");
            h.append(cookie.getPath()+"<br>");
            h.append(cookie.getValue()+"<br>");
            h.append(cookie.getVersion()+"<br>");
            h.append(cookie.getSecure()+"<br>");

        }
        return h.toString();
    }

    @RequestMapping(path = "/response")
    @ResponseBody
    public String response(@CookieValue(value = "userID",defaultValue = "toutiao") String userID,
                           @RequestParam(value = "key",defaultValue = "defaultkey") String responseKey,
                           @RequestParam(value = "value",defaultValue = "defaultvalue") String responseValue,
                           HttpServletResponse response){
        response.addCookie(new Cookie(responseKey,responseValue));
        response.addHeader("new Header "+responseKey,"new Header "+responseValue);
        return "th--------th "+userID;
    }

    @RequestMapping(path = "/redirect/{code}")
    public String redirect(@PathVariable("code") int code,
                           HttpSession session){
//        RedirectView redirectView = new RedirectView("/",true);
//        if(code == 301){
//            redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
//        }
//        return redirectView;
        session.setAttribute("userStatus","quiting and sleeping");
        return "redirect:/";
    }

    @RequestMapping(path = "/admin")
    @ResponseBody
    public String admin(@RequestParam(value = "key",defaultValue = "default") String key){
        if(key.equals("admin")){
            return "hello admin!";
        }else{
            throw new IllegalArgumentException("You are not admin!");
        }
    }

    @ExceptionHandler
    @ResponseBody
    public String exception(Exception e){
        return "ERROR "+ e.getMessage();
    }
}
