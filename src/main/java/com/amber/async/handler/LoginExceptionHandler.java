package com.amber.async.handler;

import com.amber.async.EventHandler;
import com.amber.async.EventModel;
import com.amber.async.EventType;
import com.amber.service.MailSender;
import com.amber.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LoginExceptionHandler implements EventHandler {

    @Autowired
    private MessageService messageService;

    @Autowired
    private MailSender mailSender;

    @Override
    public void doHandle(EventModel model) {

        if (model.getEtype() == EventType.LOGIN) {
            //TODO: 登录ip地址检查
            String content = "登录(from admin)";
            messageService.addMessage(3, model.getActorId(), content);
            Map<String, Object> map = new HashMap<>();
            map.put("username", model.getExt("username"));
            mailSender.sendWithTemplate(model.getExt("email"), "login  notify", "mails/welcome.html", map);
        }

    }

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.LOGIN);
    }
}
