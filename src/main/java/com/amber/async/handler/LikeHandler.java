package com.amber.async.handler;

import com.amber.async.EventHandler;
import com.amber.async.EventModel;
import com.amber.async.EventType;
import com.amber.model.EntityType;
import com.amber.model.News;
import com.amber.model.User;
import com.amber.service.MessageService;
import com.amber.service.NewsService;
import com.amber.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class LikeHandler implements EventHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private NewsService newsService;

    /**
     * @param model doHandle中要做的事情：
     *              1. 判断model是否是该Handler支持的类型
     *              2. 根据model不同的类型和参数做不同的处理
     */
    @Override
    public void doHandle(EventModel model) {
        if (model.getEntityType() == EntityType.ENTITY_NEWS) {

            int fromId = model.getActorId();
            int toId = model.getEntityOwnerId();
            User user = userService.getUserById(fromId);
            News news = newsService.getNews(model.getEntityId());

            String content = "用户 " + user.getName() + " 赞了你发表的资讯：" + news.getTitle() + "( http://127.0.0.1:8100/news/" + model.getEntityId() + " )";
            messageService.addMessage(fromId, toId, content);

        }

    }

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.LIKE);
    }
}
