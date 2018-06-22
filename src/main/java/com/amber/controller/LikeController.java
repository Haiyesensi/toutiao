package com.amber.controller;

import com.amber.async.EventModel;
import com.amber.async.EventProducer;
import com.amber.async.EventType;
import com.amber.model.EntityType;
import com.amber.model.HostHolder;
import com.amber.model.News;
import com.amber.service.LikeService;
import com.amber.service.NewsService;
import com.amber.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LikeController {

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LikeService likeService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private EventProducer eventProducer;

    @RequestMapping(path = "/like", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("newsId") int newsId) {

        int localUserId = hostHolder.getUser().getId();
        long likeCount = likeService.like(localUserId, EntityType.ENTITY_NEWS, newsId);
        newsService.updateLikeCount(newsId, (int) likeCount);

        EventModel likeEvent = new EventModel(EventType.LIKE);
        likeEvent.setActorId(localUserId);
        likeEvent.setEntityType(EntityType.ENTITY_NEWS);
        likeEvent.setEntityId(newsId);
        News news = newsService.getNews(newsId);
        likeEvent.setEntityOwnerId(news.getUserId());
        eventProducer.fireEvent(likeEvent);
        return JsonUtil.getJSONString(0, String.valueOf(likeCount));
    }

    @RequestMapping(path = "/dislike", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String dislike(@RequestParam("newsId") int newsId) {

        int localUserId = hostHolder.getUser().getId();
        long likeCount = likeService.dislike(localUserId, EntityType.ENTITY_NEWS, newsId);

        newsService.updateLikeCount(newsId, (int) likeCount);
        return JsonUtil.getJSONString(0, String.valueOf(likeCount));
    }
}
