package com.wyh.zixun;

import com.wyh.zixun.dao.CommentDAO;
import com.wyh.zixun.dao.NewsDAO;
import com.wyh.zixun.dao.LoginTicketDAO;
import com.wyh.zixun.dao.UserDAO;
import com.wyh.zixun.model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("/init-schema.sql")
public class InitDatabaseTests {
    @Autowired
    UserDAO userDAO;

    @Autowired
    NewsDAO newsDAO;

    @Autowired
    LoginTicketDAO loginTicketDAO;

    @Autowired
    CommentDAO commentDAO;

    @Test
    public void contextLoads() {
        Random random = new Random();
        for (int i=0;i<10000;++i){
            User user = new User();
            user.setHeadUrl(String.format("http://images.wyh.com/head/%dt.png",random.nextInt(1000)));
            user.setName(String.format("USER%d",i));
            user.setPassword("");
            user.setSalt("");
            userDAO.addUser(user);

            News news = new News();
            news.setCommentCount(i);
            Date date = new Date();
            date.setTime(date.getTime()-1000*3600*5*i);
            news.setCreatedDate(date);
            news.setImage(String.format("http://images.wyh.com/head/%dm.png",random.nextInt(1000)));
            news.setLikeCount(i+1);
            news.setUserId(i+1);
            news.setTitle(String.format("TITLE{%d}",i));
            news.setLink(String.format("http://www.wyh.com/%d.html",i));
            news.setCommentCount(i+2);
            newsDAO.addNews(news);

            for (int j=0;j<3;++j){
                Comment comment = new Comment();
                comment.setUserId(user.getId());
                comment.setEntityId(news.getId());
                comment.setEntityType(EntityType.ENTITY_NEWS);
                comment.setContent("评论"+String.valueOf(j));
                comment.setStatus(0);
                comment.setCreatedDate(new Date());
                commentDAO.addComment(comment);
            }


            user.setPassword("newpassword123");
            userDAO.updatePassword(user);

            LoginTicket ticket = new LoginTicket();
            ticket.setStatus(0);
            ticket.setUserId(i+1);
            ticket.setExpired(date);
            ticket.setTicket(String.format("TICKET%d",i+1));
            loginTicketDAO.addTicket(ticket);

        }

        Assert.assertNotNull(commentDAO.selectByEntity(1,EntityType.ENTITY_NEWS));
        Assert.assertNotNull(commentDAO.getCommentCount(1,EntityType.ENTITY_NEWS));


        Assert.assertEquals("newpassword123",userDAO.selectById(1).getPassword());
        userDAO.selectById(5);
        userDAO.deleteById(4);
    }

}
