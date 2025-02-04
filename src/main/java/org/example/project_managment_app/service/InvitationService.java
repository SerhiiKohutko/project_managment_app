package org.example.project_managment_app.service;

import jakarta.mail.MessagingException;
import org.example.project_managment_app.entities.Invitation;

public interface InvitationService {

    void sendInvitation(String email, long projectId) throws MessagingException;

    Invitation acceptInvitation(String token, long userId) throws Exception;

    String getTokenByUserMail(String email);

    void deleteToken(String token);
}
