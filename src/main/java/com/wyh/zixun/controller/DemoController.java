package com.wyh.zixun.controller;


import com.wyh.zixun.service.ZixunService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Enumeration;

import static org.springframework.http.HttpStatus.MOVED_PERMANENTLY;

/*
@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private ZixunService zixunService;

    @RequestMapping(path = {"/", "/index"})
    @ResponseBody
    public String index(Model model) {
        model.addAttribute("value1", 5);
        logger.info("Visit Index");
        return "index";
    }

    @RequestMapping(value = {"/profile/{groupID}/{userID}"})
    @ResponseBody
    public String profile(@PathVariable("groupID") String groupID,
                          @PathVariable("userID") int userID,
                          @RequestParam(value = "type", defaultValue = "1") int type,
                          @RequestParam(value = "key", defaultValue = "wyh") String key) {
        return String.format("%s %d %d %s", groupID, userID, type, key);
    }

    @RequestMapping(path = "/request")
    @ResponseBody
    public String request(HttpServletRequest request, HttpServletResponse response,
                          HttpSession session) {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> headerName = request.getHeaderNames();
        while (headerName.hasMoreElements()) {
            String name = headerName.nextElement();
            sb.append(name + request.getHeader(name) + "<br>");
        }

        for (Cookie cookie : request.getCookies()) {
            sb.append(cookie.getName() + "---" + cookie.getValue() + "<br>");
        }
        return sb.toString();
    }

    @RequestMapping("/response")
    @ResponseBody
    public String response(@CookieValue(value = "wyh", defaultValue = "coral")
                                   HttpServletRequest request, HttpServletResponse response,
                           HttpSession session) {
        return "1";
    }


    @RequestMapping("/redirect/{code}")
    public RedirectView redirect (@PathVariable(value="code")int code)
    {
        RedirectView red = new RedirectView("/",false);

        if (code == 301) red.setStatusCode( MOVED_PERMANENTLY);
        return red;
    }


    @ExceptionHandler
    @ResponseBody
    public String error(Exception e){
        return "111ERROR:"+e.getMessage();
    }
}

*/