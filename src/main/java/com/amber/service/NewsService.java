package com.amber.service;

import com.amber.dao.NewsDao;
import com.amber.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    NewsDao newsDao;

    public List<News> getLatestNews(int userId,int offset,int limit){
        return newsDao.selectByUserIdAndOffset(userId, offset, limit);
    }
}
