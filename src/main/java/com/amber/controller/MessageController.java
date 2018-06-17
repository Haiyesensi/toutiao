package com.amber.controller;

import com.amber.model.HostHolder;
import com.amber.model.Message;
import com.amber.model.User;
import com.amber.model.ViewObject;
import com.amber.service.MessageService;
import com.amber.service.UserService;
import com.amber.util.JsonUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MessageController {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = "/msg/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String messageList(Model model) {
        try {
            List<Message> messages = messageService.getAllConversationByUserId(hostHolder.getUser().getId(), 0, 10);
            List<ViewObject> conversationVOs = new ArrayList<>();
            for (Message message : messages) {
                ViewObject vo = new ViewObject();
                int targetId = (message.getFromId() == hostHolder.getUser().getId()) ? message.getFromId() : message.getToId();
                User user = userService.getUserById(targetId);
                int unReadCount = messageService.getUnreadCountByCid(hostHolder.getUser().getId(), message.getConversationId());
                vo.set("conversation", message);
                vo.set("userId", user.getId());
                vo.set("headUrl", user.getHeadUrl());
                vo.set("userName", user.getName());
                vo.set("unreadCount", unReadCount);
                conversationVOs.add(vo);
            }
            model.addAttribute("conversations", conversationVOs);
        } catch (Exception e) {
            logger.error("get msg list failure: " + e.getMessage());
            return JsonUtil.getJSONString(1, "get msg list error");
        }
        return "letter";
    }

    @RequestMapping(path = "/msg/addMessage", method = RequestMethod.POST)
    @ResponseBody
    public String addMessage(@RequestParam("fromId") int fromId,
                             @RequestParam("toId") int toId,
                             @RequestParam("content") String content) {
        try {
            content = HtmlUtils.htmlEscape(content);
            messageService.addMessage(fromId, toId, content);
        } catch (Exception e) {
            logger.error("message send failure: " + e.getMessage());
            return JsonUtil.getJSONString(1, "message send failure!");
        }
        return JsonUtil.getJSONString(0, "message add success");
    }

    @RequestMapping(path = "/msg/detail", method = RequestMethod.GET)
    public String conversationDetail(@Param("cid") String cid, Model model) {
        try {
            List<Message> mesages = messageService.getAllMessageInConversation(cid, 0, 10);
            List<ViewObject> messageVOs = new ArrayList<>();
            for (Message message : mesages) {
                ViewObject vo = new ViewObject();
                vo.set("message", message);
                vo.set("headUrl", userService.getUserById(message.getFromId()).getHeadUrl());
                messageVOs.add(vo);
            }
            model.addAttribute("messages", messageVOs);
        } catch (Exception e) {
            logger.error("get conversation detail failure: " + e.getMessage());
            return JsonUtil.getJSONString(1, "get conversation detail error!");
        }
        return "letterDetail";
    }
}
