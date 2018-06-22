package com.amber.async.handler;

import com.amber.async.EventHandler;
import com.amber.async.EventModel;
import com.amber.async.EventType;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class LikeHandler implements EventHandler {
    /**
     * @param model doHandle中要做的事情：
     *              1. 判断model是否是该Handler支持的类型
     *              2. 根据model不同的类型和参数做不同的处理
     */
    @Override
    public void doHandle(EventModel model) {

        System.out.println("liked");
    }

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.LIKE);
    }
}
