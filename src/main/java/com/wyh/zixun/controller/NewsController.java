package com.wyh.zixun.controller;

import com.wyh.zixun.model.*;
import com.wyh.zixun.service.CommentService;
import com.wyh.zixun.service.LikeService;
import com.wyh.zixun.service.NewsService;
import com.wyh.zixun.service.UserService;
import com.wyh.zixun.util.ZixunUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class NewsController {
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    HostHolder hostHolder;

    @Autowired
    NewsService newsService;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @Autowired
    LikeService likeService;


    //添加资讯
    @RequestMapping(path = {"/user/addNews/"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addNews(@RequestParam("image") String image,
                          @RequestParam("title") String title,
                          @RequestParam("link") String link) {
        try {
            News news = new News();
            if (hostHolder.getUser() != null) {
                news.setUserId(hostHolder.getUser().getId());
            } else {
                //匿名用户
                news.setUserId(999);
            }
            news.setImage(image);
            news.setCreatedDate(new Date());
            news.setTitle(title);
            news.setLink(link);
            newsService.addNews(news);
            return ZixunUtil.getJSONString(0);
        } catch (Exception e) {
            logger.error("添加资讯错误" + e.getMessage());
            return null;
        }
    }


    //打开资讯
    @RequestMapping(path = {"/news/{newsId}"}, method = {RequestMethod.GET})
    public String newsDetail(@PathVariable("newsId") int newsId, Model model) {
        News news = newsService.getById(newsId);

            int localUserId = hostHolder.getUser() != null? hostHolder.getUser().getId():0;
            if (localUserId != 0){
                model.addAttribute("like",likeService.getLikeStatus(localUserId,EntityType.ENTITY_NEWS,news.getId()));
            }else{
                model.addAttribute("like",0);
            }
        if (news != null) {
            List<Comment> comments = commentService.getCommentByEntity(news.getId(), EntityType.ENTITY_NEWS);
            List<ViewObject> commentVOS = new ArrayList<>();
            for (Comment comment : comments) {
                ViewObject vo = new ViewObject();
                vo.set("comment", comment);
                vo.set("user", userService.getUser(comment.getUserId()));
                commentVOS.add(vo);
            }
            model.addAttribute("comments", commentVOS);
        }
        model.addAttribute("news", news);
        model.addAttribute("owner", userService.getUser(news.getUserId()));
        return "detail";
    }

    //评论功能
    @RequestMapping(path = {"/addComment"}, method = {RequestMethod.POST})
    public String addComment(@RequestParam("newsId") int newsId,
                             @RequestParam("content") String content) {
        try {
            Comment comment = new Comment();
            comment.setUserId(hostHolder.getUser().getId());
            comment.setEntityId(newsId);
            comment.setEntityType(EntityType.ENTITY_NEWS);
            comment.setContent(content);
            comment.setCreatedDate(new Date());
            comment.setStatus(0);

            commentService.addComment(comment);
            //更新评论数量

            int count = commentService.getCommentCount(comment.getEntityId(), comment.getEntityType());
            newsService.updateCommentCount(comment.getEntityId(), count);

            //怎么异步化
        } catch (Exception e) {
            logger.error("增加评论失败" + e.getMessage());
        }
        return "redirect:/news/" + String.valueOf(newsId);
    }

    @RequestMapping(path = {"/deleteComment"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String deleteComment(@RequestParam("newsId") int newsId,
                                @RequestParam("commentId") int commentId
    ) {
        try {
            commentService.deleteComment(commentId);
            int count = commentService.getCommentCount(newsId, EntityType.ENTITY_NEWS);
            newsService.updateCommentCount(newsId, count);
        } catch (Exception e) {
            logger.error("删除评论失败" + e.getMessage());
        }
        return "redirect:/news/" + String.valueOf(newsId);
    }


    //图片功能

    @RequestMapping(path = {"/uploadImage/"}, method = {RequestMethod.POST})
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = newsService.saveImage(file);
            if (fileUrl == null) {
                return ZixunUtil.getJSONString(1, "上传失败");
            }
            return ZixunUtil.getJSONString(0, fileUrl);
        } catch (Exception e) {
            logger.error("上传图片失败" + e.getMessage());
            return ZixunUtil.getJSONString(1, "上传失败");
        }
    }

    @RequestMapping(path = {"/image"}, method = {RequestMethod.GET})
    @ResponseBody
    public void getImage(@RequestParam("name") String imageName,
                         HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");
            StreamUtils.copy(new FileInputStream(new
                            File(ZixunUtil.IMAGE_DIR + imageName)),
                    response.getOutputStream());
        } catch (Exception e) {
            logger.error("读取图片错误" + e.getMessage());
        }
    }

    //


}
