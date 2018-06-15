package com.amber.service;

import com.amber.dao.CommentDao;
import com.amber.dao.NewsDao;
import com.amber.model.Comment;
import com.amber.model.EntityType;
import com.amber.model.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private NewsDao newsDao;

    @Autowired
    private HostHolder hostHolder;

    public void addComment(int newsId, String content) {
        Comment comment = new Comment();
        comment.setEntityId(newsId);
        comment.setEntityType(EntityType.ENTITY_NEWS);
        comment.setContent(content);
        comment.setCreatedDate(new Date());
        comment.setUserId(hostHolder.getUser().getId());
        commentDao.addComment(comment);
        newsDao.updateNewsCommentCount(newsDao.selectCommentCount(newsId) + 1, newsId);
    }

    public void deleteComment(int newsId) {
        commentDao.deleteComment(newsId, EntityType.ENTITY_NEWS);
        int commentCount = newsDao.selectCommentCount(newsId);
        newsDao.updateNewsCommentCount(commentCount - 1, newsId);
    }


    public List<Comment> getCommentByEntity(int entity_id, int entity_type) {
        return commentDao.selectCommentByEntity(entity_id, entity_type);
    }

}
