package com.amber.Controller;

import com.amber.dao.NewsDao;
import com.amber.dao.UserDao;
import com.amber.model.News;
import com.amber.model.User;
import com.amber.model.ViewObject;
import com.amber.service.NewsService;
import com.amber.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
public class HomeController {

//    @Autowired
//    NewsDao newsDao;
//
//    @Autowired
//    UserDao userDao;

    @Autowired
    NewsService newsService;

    @Autowired
    UserService userService;

    private List<ViewObject> getNews(int userId, int offset, int limit) {
        List<News> newsList = newsService.getLatestNews(userId, offset, limit);
        List<ViewObject> vos = new ArrayList<>();
        for (News news : newsList) {
            ViewObject vo = new ViewObject();
            vo.set("news", news);
            vo.set("user", userService.getUser(news.getUserId()));
            vos.add(vo);
        }
        return vos;
    }
    @RequestMapping(path = "/",method = {RequestMethod.GET,RequestMethod.POST})
    public String index(Model model){
//        initSql();
        model.addAttribute("vos",getNews(0,0,10));
        return "home";
    }

    @RequestMapping(path = {"/user/{userId}"},method ={RequestMethod.GET,RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("userId") int userId){
        model.addAttribute("vos",getNews(userId,0,10));
        return "home";
    }

//    private void initSql() {
//        Random random = new Random();
//        for (int i = 0; i < 11; i++) {
//            News news = new News();
//            news.setCommentCount(i);
//            Date date = new Date();
//            date.setTime(date.getTime() + 1000*3600*5*i);
//            news.setCreatedDate(date);
//            news.setImage(String.format("http://images.nowcoder.com/head/%dm.png", random.nextInt(1000)));
//            news.setLikeCount(i+1);
//            news.setUserId(i+1);
//            news.setTitle(String.format("TITLE{%d}", i));
//            news.setLink(String.format("http://www.nowcoder.com/%d.html", i));
//            newsDao.addNews(news);
//
//            User user = new User();
//            user.setHeadUrl(String.format("images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
//            user.setName(String.format("user %d", i));
//            user.setPassword("is a new password");
//            user.setSalt("");
//            userDao.addUser(user);
//        }
//    }
}
