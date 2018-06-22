package com.amber.async.handler;

import com.amber.async.EventHandler;
import com.amber.async.EventModel;
import com.amber.async.EventType;
import com.amber.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class LoginExceptionHandler implements EventHandler {

    @Autowired
    private MessageService messageService;

    @Override
    public void doHandle(EventModel model) {

        if (model.getEtype() == EventType.LOGIN) {
            //TODO: 登录ip地址检查
            String content = "你的登录ip地址异常(from admin)";
            messageService.addMessage(3, model.getActorId(), content);
        }

    }

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.LOGIN);
    }
}
