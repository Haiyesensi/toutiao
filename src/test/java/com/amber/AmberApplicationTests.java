package com.amber;

import com.AmberApplication;
import com.amber.dao.CommentDao;
import com.amber.dao.MessageDao;
import com.amber.dao.TicketDao;
import com.amber.model.*;
import com.amber.service.MessageService;
import com.amber.service.UserService;
import com.amber.util.JedisAdapter;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AmberApplication.class)
public class AmberApplicationTests {

    @Autowired
    TicketDao ticketDao;

    @Autowired
    CommentDao commentDao;

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @Autowired
    MessageDao messageDao;

    @Autowired
    JedisAdapter jedisAdapter;

    @Test
    public void contextLoads() {
        for (int i = 0; i < 11; i++) {
            Ticket ticket = new Ticket();
            ticket.setStatus(0);
            Date date = new Date();
            date.setTime(date.getTime() + 1000 * 3600 * 24);
            ticket.setExpired(date);
            ticket.setUserId(i + 1);
            ticket.setTicket(String.format("Ticket%d", i));
            ticketDao.addTicket(ticket);
            ticketDao.updateStatus(2, String.format("Ticket%d", i));
        }
        Assert.assertEquals(4, ticketDao.selectByTicket("Ticket3").getId());
    }

    @Test
    public void add_comment_test() {
        for (int i = 0; i < 3; i++) {
            Comment comment = new Comment();
            comment.setContent("this is a test comment" + String.valueOf(i));
            comment.setCreatedDate(new Date());
            comment.setEntityId(14);
            comment.setEntityType(EntityType.ENTITY_NEWS);
            comment.setUserId(46);
            Assert.assertEquals(1, commentDao.addComment(comment));
        }
    }

    @Test
    public void select_user_from_comment_id_test() {
        int expectedUserId = 46;
        int newsId = 14;
        List<Comment> comments = commentDao.selectCommentByEntity(newsId, EntityType.ENTITY_NEWS);
        for (Comment comment : comments) {
            Assert.assertEquals(expectedUserId, userService.getUserById(comment.getUserId()).getId());
        }

    }

    @Test
    public void add_message_should_succesful_test() {
        int expectFromId = 46;

        Message message = new Message();
        message.setFromId(46);
        message.setToId(47);
        message.setContent("hello");
        message.setCreatedDate(new Date());
        message.setConversationId("46_47");
        Assert.assertEquals(1, messageDao.addMessage(message));
        Assert.assertEquals(expectFromId, messageDao.selectAllMessageByCid("46_47", 0, 1).get(0).getFromId());
    }

    @Test
    public void should_equals_seri_user() {
        User user = new User();
        user.setName("name");
        user.setHeadUrl("https://sakdjoiadna");
        user.setPassword("auidadas");
        user.setSalt("asd178");
        jedisAdapter.setObject("u", user);
        User user1 = jedisAdapter.getObject("u", User.class);
        Assert.assertEquals("name", user1.getName());
        System.out.println(ToStringBuilder.reflectionToString(user1));
    }

}
