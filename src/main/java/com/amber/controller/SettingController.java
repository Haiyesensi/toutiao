package com.amber.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SettingController {

    @RequestMapping(value = "/setting",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String setting(){
        return "setting:";
    }
}
