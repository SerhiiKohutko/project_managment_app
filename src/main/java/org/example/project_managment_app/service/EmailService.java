package org.example.project_managment_app.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    public void sendEmailWithToken(String userEmail, String link) throws MessagingException;
}
