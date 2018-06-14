package com.amber.service;

import com.amber.dao.CommentDao;
import com.amber.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    public void addComment() {
        Comment comment = new Comment();
        commentDao.addComment(comment);
    }

    public List<Comment> getCommentByEntity(int entity_id, int entity_type) {
        return commentDao.selectCommentByEntity(entity_id, entity_type);
    }

}
