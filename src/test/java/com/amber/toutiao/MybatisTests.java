package com.amber.toutiao;

import com.amber.toutiao.Dao.UserDao;
import com.amber.toutiao.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = com.amber.toutiao.ToutiaoApplication.class)
@Sql("/init-schema.sql")
public class MybatisTests{

    @Autowired
    UserDao userDao;

    @Test
    public void contextLoads() {
        Random random = new Random();
        for(int i = 0; i < 11; i++){
            User user = new User();
            user.setHeadUrl(String.format("images.newcoder.com/head/%dt.png",random.nextInt(1000)));
            user.setName(String.format("user %d",i));
            user.setPassword("");
            user.setSalt("");
            userDao.addUser(user);
        }
    }

}
