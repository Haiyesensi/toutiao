package com.amber.toutiao.Controller;


import com.amber.toutiao.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HttpServletBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;


@Controller
public class IndexController {

    @RequestMapping(path = {"/","/index"})
    @ResponseBody
    public String getString(){
        return "home.vm";
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
        return h.toString();
    }

}
