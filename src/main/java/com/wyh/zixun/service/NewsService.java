package com.wyh.zixun.service;

import com.wyh.zixun.dao.NewsDAO;
import com.wyh.zixun.model.Comment;
import com.wyh.zixun.model.EntityType;
import com.wyh.zixun.model.News;
import com.wyh.zixun.model.ViewObject;
import com.wyh.zixun.util.ZixunUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class NewsService {


    @Autowired
    private NewsDAO newsDAO;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    public List<News> getLatestNews(int userId, int offset, int limit) {
        return newsDAO.selectByUserIdAndOffset(userId, offset, limit);
    }


    public int getNewsCount(){
        return newsDAO.getNewsCount();
    }



    public int addNews(News news) {
        newsDAO.addNews(news);
        return news.getId();
    }

    public void updateCommentCount(int newsId , int count){
        newsDAO.updateCommentCount(newsId,count);
    }

    public void updateLikeCount(int newsId , int count){
        newsDAO.updateLikeCount(newsId,count);
    }


    public News getById(int newsId) {
        return newsDAO.getById(newsId);
    }

    public String saveImage(MultipartFile file) throws IOException {
        int dotPos = file.getOriginalFilename().lastIndexOf(".");
        if (dotPos < 0) {
            return null;
        }
        String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
        if (!ZixunUtil.isFileAllowed(fileExt)) {
            return null;
        }

        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
        Files.copy(file.getInputStream(), new File(ZixunUtil.IMAGE_DIR + fileName).toPath()
                , StandardCopyOption.REPLACE_EXISTING);
        return ZixunUtil.ZIXUN_DOMAIN + "image?name=" + fileName;
    }
}
