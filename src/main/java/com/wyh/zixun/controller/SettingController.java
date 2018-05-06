package com.wyh.zixun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SettingController {
    @RequestMapping("/setting")
    @ResponseBody
    public String setting(){
        return "Setting:OK";
    }
}