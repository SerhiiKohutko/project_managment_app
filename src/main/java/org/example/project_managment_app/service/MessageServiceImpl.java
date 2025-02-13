package org.example.project_managment_app.service;

import org.example.project_managment_app.entities.Chat;
import org.example.project_managment_app.entities.Message;
import org.example.project_managment_app.entities.User;
import org.example.project_managment_app.repository.MessageRepository;
import org.example.project_managment_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectService projectService;

    @Override
    public Message sendMessage(Long senderId, Long chatId, String content) throws Exception {
        User user = userRepository.findById(senderId).orElseThrow(() -> new Exception("User not found with id: " + senderId));

        Chat chat = projectService.getProjectById(chatId).getChat();

        Message message = new Message();
        message.setContent(content);
        message.setSender(user);
        message.setChat(chat);
        message.setCreatedAt(LocalDateTime.now());
        Message savedMessage = messageRepository.save(message);

        chat.getMessages().add(savedMessage);
        return savedMessage;
    }

    @Override
    public List<Message> getMessagesByProjectId(Long projectId) throws Exception {
        Chat chat = projectService.getChatByProjectId(projectId);
        return messageRepository.findByChatIdOrderByCreatedAtAsc(chat.getId());
    }
}
