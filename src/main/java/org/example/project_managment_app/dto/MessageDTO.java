package org.example.project_managment_app.dto;

import lombok.Data;
import org.example.project_managment_app.entities.User;

@Data
public class MessageDTO {
    private String content;
    private Long senderId;
    private Long projectId;
    private User sender;
}
