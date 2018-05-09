package com.amber.service;

import com.amber.model.User;
import com.amber.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public User getUser(int id){
        return userDao.selectUserByID(id);
    }
}
