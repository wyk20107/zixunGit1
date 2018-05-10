package com.wyh.zixun.service;

import com.wyh.zixun.dao.CommentDAO;
import com.wyh.zixun.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentDAO commentDAO;

    public List<Comment> getCommentByEntity(int entityId,String entityType){
        return commentDAO.selectByEntity(entityId,entityType);
    }

    public int addComment(Comment comment){
        return commentDAO.addComment(comment);
    }

    public int getCommentCount(int entityId,String entityType){
        return commentDAO.getCommentCount(entityId,entityType);
    }

    public int deleteComment(int id){
        return commentDAO.updateCommentStatus(id);
    }
}
