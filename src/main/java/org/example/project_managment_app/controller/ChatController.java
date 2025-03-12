package org.example.project_managment_app.controller;

import org.example.project_managment_app.dto.MessageDTO;
import org.example.project_managment_app.entities.User;
import org.example.project_managment_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @Autowired
    private UserService userService;

    @MessageMapping("/chat")
    @SendTo("/topic/public")
    public MessageDTO sendMessage(MessageDTO messageDTO) throws Exception {
        System.out.println(messageDTO.getContent());
        User sender = userService.findUserById(messageDTO.getSenderId());
        messageDTO.setSender(sender);
        return messageDTO;
    }
}
