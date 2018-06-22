package com.amber.async;

import com.alibaba.fastjson.JSON;
import com.amber.util.JedisAdapter;
import com.amber.util.RedisKeyUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(EventProducer.class);

    @Autowired
    private JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel model) {
        try {
            String json = JSON.toJSONString(model);
            String key = RedisKeyUtil.getBizEvent();
            jedisAdapter.lpush(key, json);
            return true;
        } catch (Exception e) {
            logger.error("exception in fireevent: " + e.getMessage());
            return false;
        }
    }
}
