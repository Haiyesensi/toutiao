package com.amber.async.handler;

import com.amber.async.EventHandler;
import com.amber.async.EventModel;
import com.amber.async.EventType;

import java.util.Arrays;
import java.util.List;

public class MailHandler implements EventHandler {
    @Override
    public void doHandle(EventModel model) {
        System.out.println("send mail");
    }

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.MAIL);
    }
}
