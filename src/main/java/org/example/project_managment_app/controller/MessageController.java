package org.example.project_managment_app.controller;

import org.example.project_managment_app.entities.Chat;
import org.example.project_managment_app.entities.Message;
import org.example.project_managment_app.entities.User;
import org.example.project_managment_app.request.CreateMessageRequest;
import org.example.project_managment_app.service.MessageService;
import org.example.project_managment_app.service.ProjectService;
import org.example.project_managment_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody CreateMessageRequest req) throws Exception {

        User user = userService.findUserById(req.getSenderId());

        Chat chats = projectService.getProjectById(req.getProjectId()).getChat();

        if (chats == null) {throw new Exception("Chat not found");}

        Message sentMessage = messageService.sendMessage(req.getSenderId(), req.getProjectId(), req.getContent());
        return ResponseEntity.ok(sentMessage);
    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>> getMessagesByChatId(@PathVariable("projectId") long projectId) throws Exception {
        List<Message> messages = messageService.getMessagesByProjectId(projectId);
        return ResponseEntity.ok(messages);
    }
}
