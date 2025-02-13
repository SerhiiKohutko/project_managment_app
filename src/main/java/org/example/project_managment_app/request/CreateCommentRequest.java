package org.example.project_managment_app.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateCommentRequest {
    private Long issueId;
    private String content;

}
