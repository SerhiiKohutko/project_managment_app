package org.example.project_managment_app.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateMessageRequest {
    private Long senderId;
    private String content;
    private Long projectId;
}
