package com.amber;

import com.amber.dao.NewsDao;
import com.amber.dao.UserDao;
import com.amber.model.News;
import com.amber.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ToutiaoApplication.class)
@Sql("/init-schema.sql")
public class MybatisTests{

    @Autowired
    UserDao userDao;

    @Autowired
    NewsDao newsDao;

    @Test
    public void contextLoads() {
        Random random = new Random();
//        DateFormat dateFormat = new SimpleDateFormat("YYYY-mm-dd hh:MM:ss");
//        for(int i = 0; i < 11; i++){
//            User user = new User();
//            user.setHeadUrl(String.format("images.newcoder.com/head/%dt.png",random.nextInt(1000)));
//            user.setName(String.format("user %d",i));
//            user.setPassword("");
//            user.setSalt("");
//            userDao.addUser(user);
//            user.setPassword("is a new password");
//            userDao.updatePassowed(user);
//        }

        for(int i = 0; i < 11;i++){
            News news = new News();
            news.setTitle(String.format("title %d",random.nextInt(1000)));
            news.setLink(String.format("https://hyss.me/%d",random.nextInt(100)));
            news.setImage(String.format("https://images.newcoder.com/head/%dm.png",random.nextInt(1000)));
            news.setLikeCount(random.nextInt(100));
            news.setCommentCount(random.nextInt(100));
            news.setCreatedDate(new Date(System.currentTimeMillis()+1000*3600*i));
            news.setUserId(i+1);
            newsDao.addNews(news);
        }
    }

}
