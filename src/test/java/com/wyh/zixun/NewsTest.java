package com.wyh.zixun;

import com.wyh.zixun.dao.NewsDAO;
import com.wyh.zixun.dao.UserDAO;
import com.wyh.zixun.model.News;
import com.wyh.zixun.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Random;


@RunWith(SpringRunner.class)
@SpringBootTest

public class NewsTest {

    @Autowired
    UserDAO userDAO;

    @Autowired
    NewsDAO newsDAO;

    @Test
    public void contextLoads() {
        Random random = new Random();
        for (int i = 100; i < 100000; ++i) {
            User user = new User();
            user.setHeadUrl(String.format("http://images.wyh.com/head/%dt.png", random.nextInt(1000)));
            user.setName(String.format("USER%d", i));
            user.setPassword("");
            user.setSalt("");
            userDAO.addUser(user);

            News news = new News();
            news.setCommentCount(i);
            Date date = new Date();
            date.setTime(date.getTime() - 1000 * 3600 * 5 * i);
            news.setCreatedDate(date);
            news.setImage(String.format("http://images.wyh.com/head/%dm.png", random.nextInt(1000)));
            news.setLikeCount(i + 1);
            news.setUserId(i + 1);
            news.setTitle(String.format("TITLE{%d}", i));
            news.setLink(String.format("http://www.wyh.com/%d.html", i));
            news.setCommentCount(i + 2);
            newsDAO.addNews(news);
        }
    }
}
