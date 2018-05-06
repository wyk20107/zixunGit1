package com.wyh.zixun;


import com.wyh.zixun.dao.CommentDAO;
import com.wyh.zixun.model.User;
import com.wyh.zixun.util.JedisAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyTest {
    @Autowired
    CommentDAO commentDAO;

    @Autowired
    JedisAdapter jedisAdapter;

    @Test
    public void trysth() {
        commentDAO.updateCommentStatus(38);
        User user = new User();
        user.setHeadUrl("111");
        user.setName("xxx");
        user.setPassword("ddd");
        user.setSalt("fff");

        jedisAdapter.setObject("userxxx",user);

        User u = jedisAdapter.getObject("userxxx",User.class);


    }

}
