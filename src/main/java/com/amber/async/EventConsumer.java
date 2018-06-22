package com.amber.async;

import com.alibaba.fastjson.JSON;
import com.amber.util.JedisAdapter;
import com.amber.util.RedisKeyUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware {

    @Autowired
    private JedisAdapter jedisAdapter;

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(EventConsumer.class);

    private Map<EventType, List<EventHandler>> config = new HashMap<>();
    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        /**
         * 初始化config
         */
        //得到程序中所有EventHandler接口的实现类，将其注册在config中
        Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        if (beans != null) {
            for (Map.Entry<String, EventHandler> entry : beans.entrySet()) {
                List<EventType> eventTypes = entry.getValue().getSupportEventType();
                for (EventType type : eventTypes) {
                    if (!config.containsKey(type)) {
                        config.put(type, new ArrayList<>());
                    }
                    config.get(type).add(entry.getValue());
                }
            }
        }

        Thread thread = new Thread(() -> {
            while (true) {
                String key = RedisKeyUtil.getBizEvent();
                List<String> events = jedisAdapter.brpop(0, key);
                for (String message : events) {
                    if (message.equals(key)) {
                        continue;
                    }
                    EventModel model = JSON.parseObject(message, EventModel.class);
                    if (!config.containsKey(model.getEtype())) {
                        logger.error("不能识别的事件");
                        continue;
                    }
                    for (EventHandler eventHandler : config.get(model.getEtype())) {
                        eventHandler.doHandle(model);
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
