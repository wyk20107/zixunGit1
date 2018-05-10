package com.wyh.zixun.controller;

import com.wyh.zixun.async.EventModel;
import com.wyh.zixun.async.EventProducer;
import com.wyh.zixun.async.EventType;
import com.wyh.zixun.model.EntityType;
import com.wyh.zixun.model.HostHolder;
import com.wyh.zixun.model.News;
import com.wyh.zixun.service.LikeService;
import com.wyh.zixun.service.NewsService;
import com.wyh.zixun.util.ZixunUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LikeController {
    @Autowired
    HostHolder hostHolder;

    @Autowired
    LikeService likeService;

    @Autowired
    NewsService newsService;

    @Autowired
    EventProducer eventProducer;

    @RequestMapping(path = {"/like"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String like(Model model, @RequestParam("newsId") int newsId) {
        int userId = hostHolder.getUser().getId();
        long likeCount = likeService.like(userId, EntityType.ENTITY_NEWS, newsId);
        News news = newsService.getById(newsId);
        newsService.updateLikeCount(newsId, (int) likeCount);

        eventProducer.fireEvent(new EventModel(EventType.LIKE).setActorId
                (hostHolder.getUser().getId()).setEntityId(newsId).setEntityId(newsId).setEntityType(EntityType.ENTITY_NEWS).setEntityOwnerId(news.getUserId()));

        return ZixunUtil.getJSONString(0, String.valueOf(likeCount));
    }


    @RequestMapping(path = {"/dislike"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String disLike(Model model, @RequestParam("newsId") int newsId) {
        int userId = hostHolder.getUser().getId();
        long likeCount = likeService.disLike(userId, EntityType.ENTITY_NEWS, newsId);
        newsService.updateLikeCount(newsId, (int) likeCount);
        return ZixunUtil.getJSONString(0, String.valueOf(likeCount));
    }
}
