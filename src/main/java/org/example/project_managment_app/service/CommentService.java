package org.example.project_managment_app.service;

import org.example.project_managment_app.entities.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(long issueId, long userId, String content) throws Exception;
    void deleteComment(long commentId, long userId) throws Exception;
    List<Comment> findCommentByIssueId(long issueId);
}
