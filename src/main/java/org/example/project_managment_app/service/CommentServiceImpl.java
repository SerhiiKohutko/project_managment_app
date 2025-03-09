package org.example.project_managment_app.service;

import org.example.project_managment_app.entities.Comment;
import org.example.project_managment_app.entities.Issue;
import org.example.project_managment_app.entities.User;
import org.example.project_managment_app.repository.CommentRepository;
import org.example.project_managment_app.repository.IssueRepository;
import org.example.project_managment_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Comment createComment(long issueId, long userId, String content) throws Exception {
        Optional<Issue> issueOptional = issueRepository.findById(issueId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (issueOptional.isEmpty() || userOptional.isEmpty()) {
            throw new Exception("User or issue not found with such id" + issueId + " " + userId);
        }


        Issue issue = issueOptional.get();
        User user = userOptional.get();

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setIssue(issue);
        comment.setUser(user);
        comment.setCreatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);
        issue.getComments().add(savedComment);

        return savedComment;
    }

    @Override
    public void deleteComment(long commentId, long userId) throws Exception {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (commentOptional.isEmpty() || userOptional.isEmpty()) {
            throw new Exception("User or issue not found with such id " + commentId + " " + userId);
        }


        Comment comment = commentOptional.get();
        User user = userOptional.get();

        if (comment.getUser().equals(user)) {
            commentRepository.delete(comment);
        } else {
            throw new Exception("User doesnt have permission to delete this comment");
        }
    }

    @Override
    public List<Comment> findCommentByIssueId(long issueId) {
        return commentRepository.findByIssueId(issueId);
    }
}
