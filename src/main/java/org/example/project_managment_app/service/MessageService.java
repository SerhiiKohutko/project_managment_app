package org.example.project_managment_app.service;

import org.example.project_managment_app.entities.Message;

import java.util.List;

public interface MessageService {

    Message sendMessage(Long senderId, Long chatId, String content) throws Exception;
    List<Message> getMessagesByProjectId(Long projectId) throws Exception;
}
