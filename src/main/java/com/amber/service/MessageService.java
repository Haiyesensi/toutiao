package com.amber.service;

import com.amber.dao.MessageDao;
import com.amber.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageDao messageDao;


    public void addMessage(int fromId, int toId, String content) {
        Message message = new Message();
        message.setFromId(fromId);
        message.setToId(toId);
        message.setContent(content);
        message.setCreatedDate(new Date());
        message.setConversationId(fromId < toId ? String.format("%d_%d", fromId, toId) : String.format("%d_%d", toId, fromId));
        messageDao.addMessage(message);
    }

    public List<Message> getAllMessageInConversation(String cid, int offset, int limit) {
        messageDao.updateReadStatusByCid(cid);
        return messageDao.selectAllMessageByCid(cid, offset, limit);
    }

    public List<Message> getAllConversationByUserId(int userId, int offset, int limit) {
        return messageDao.selectAllConversationByUserId(userId, offset, limit);
    }

    public int getUnreadCountByCid(int userId, String cid) {
        return messageDao.selectUnreadMessageCountByCid(userId, cid);
    }
}
