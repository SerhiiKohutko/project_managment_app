package org.example.project_managment_app.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
