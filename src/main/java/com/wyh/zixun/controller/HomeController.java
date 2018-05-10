package com.wyh.zixun.controller;

import com.wyh.zixun.model.EntityType;
import com.wyh.zixun.model.HostHolder;
import com.wyh.zixun.model.News;
import com.wyh.zixun.model.ViewObject;
import com.wyh.zixun.service.CommentService;
import com.wyh.zixun.service.LikeService;
import com.wyh.zixun.service.NewsService;
import com.wyh.zixun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class HomeController {
    @Autowired
    NewsService newsService;

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    @Autowired
    LikeService likeService;

    private List<ViewObject> getNews(int userId, int offset, int limit) {
        int localUserId = hostHolder.getUser() !=null?hostHolder.getUser().getId():0;
        List<News> newsList = newsService.getLatestNews(userId,offset, limit);
        List<ViewObject> vos = new ArrayList<>();
        for (News news : newsList) {
            int count = commentService.getCommentCount(news.getId(), EntityType.ENTITY_NEWS);
            newsService.updateCommentCount(news.getId(), count);
            ViewObject vo = new ViewObject();
            vo.set("news", news);
            vo.set("user", userService.getUser(news.getUserId()));
            vos.add(vo);

            if (localUserId != 0){
                vo.set("like",likeService.getLikeStatus(localUserId,EntityType.ENTITY_NEWS,news.getId()));
            }else{
                vo.set("like",0);
            }
        }
        return vos;
    }


    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model,
                        @RequestParam(value = "pop",defaultValue = "0" )int pop,
                        @RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        model.addAttribute("vos", getNews(0, pageIndex * pageSize, pageSize));
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("count",newsService.getNewsCount());
        model.addAttribute("pop",pop);
        return "home";
    }

    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("userId") int userId, @RequestParam(value = "pop", defaultValue = "0") int pop) {
        model.addAttribute("vos", getNews(userId, 0, 10));
        return "home";
    }



}