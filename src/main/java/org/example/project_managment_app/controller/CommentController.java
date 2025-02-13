package org.example.project_managment_app.controller;

import org.example.project_managment_app.entities.Comment;
import org.example.project_managment_app.entities.User;
import org.example.project_managment_app.request.CreateCommentRequest;
import org.example.project_managment_app.response.MessageResponse;
import org.example.project_managment_app.service.CommentService;
import org.example.project_managment_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Comment> createComment(
            @RequestBody CreateCommentRequest req,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        Comment createdComment = commentService.createComment(req.getIssueId(), user.getId(), req.getContent());
        return ResponseEntity.ok(createdComment);

    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<MessageResponse> deleteComment(@PathVariable Long commentId, @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        commentService.deleteComment(commentId, user.getId());
        MessageResponse messageResponse = new MessageResponse("Comment deleted successfully");
        return ResponseEntity.ok(messageResponse);
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<List<Comment>> getCommentsByIssueId(@PathVariable Long issueId) throws Exception {
        List<Comment> comments = commentService.findCommentByIssueId(issueId);
        return ResponseEntity.ok(comments);
    }

}
