package com.amber.service;

import com.amber.aspect.LogAspect;
import com.amber.dao.TicketDao;
import com.amber.dao.UserDao;
import com.amber.model.HostHolder;
import com.amber.model.Ticket;
import com.amber.model.User;
import com.amber.util.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    TicketDao ticketDao;

    @Autowired
    HostHolder hostHolder;

    public User getUserById(int id) {
        return userDao.selectById(id);
    }

    public Map<String, Object> register(String username, String password) {

        Map<String, Object> map = new HashMap<>();


        if (StringUtils.isBlank(username)) {
            map.put("errmsg1", " 用户名不能为空!");
            return map;
        }

        if (StringUtils.isBlank(password)) {
            map.put("errmsg2", " 密码不能为空");
            return map;
        }

        if (userDao.selectByName(username) != null) {
            map.put("errmsg3", "用户名已经被注册");
            return map;
        }

        User user = new User();
        user.setName(username);
        user.setHeadUrl(String.format("https://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        user.setPassword(MD5Util.encrypt(password + user.getSalt()));
        userDao.addUser(user);

        String ticket = addTicket(user.getId());
        map.put("ticket", ticket);

        return map;
    }

    public Map<String, Object> login(String username, String password) {

        Map<String, Object> map = new HashMap<>();

        if (StringUtils.isBlank(username)) {
            map.put("errmsg1", " 用户名不能为空!");
            return map;
        }

        if (StringUtils.isBlank(password)) {
            map.put("errmsg2", " 密码不能为空");
            return map;
        }
        //MD5(明文密码+salt字段) =?= 用户密文
        User user = userDao.selectByName(username);
        map.put("user", user);
        if (!MD5Util.encrypt(password + user.getSalt()).equals(user.getPassword())) {
            map.put("errmsg4", "密码不正确");
            return map;
        }

        Ticket ticket = ticketDao.selectByUserId(user.getId());
        ticketDao.updateStatus(0, ticket.getTicket());

        Date date = new Date();
        date.setTime(date.getTime() + 1000 * 3600 * 24);
        ticketDao.updateExpired(date, ticket.getTicket());

        map.put("ticket", ticket.getTicket());

        return map;
    }

    private String addTicket(int userId) {

        Ticket ticket = new Ticket();

        ticket.setUserId(userId);

        Date date = new Date();
        date.setTime(date.getTime() + 1000 * 3600 * 24);
        ticket.setExpired(date);

        ticket.setStatus(0);

        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));

        ticketDao.addTicket(ticket);

        return ticket.getTicket();
    }

    public void logout(String ticket) {
        ticketDao.updateStatus(1, ticket);
    }
}
