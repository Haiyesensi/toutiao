package com.amber.controller;

import com.amber.service.CommentService;
import com.amber.service.NewsService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentController {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    CommentService commentService;

    @Autowired
    NewsService newsService;

    @RequestMapping(path = "/addComment", method = RequestMethod.POST)
    public String addComment(@RequestParam("newsId") int newsId,
                             @RequestParam("content") String content) {
        try {
            commentService.addComment(newsId, content);
        } catch (Exception e) {
            logger.error(" add comment error" + e.getMessage());
        }
        return "redirect:/news/" + String.valueOf(newsId);
    }
}
