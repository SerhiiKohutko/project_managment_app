package org.example.project_managment_app.service;

import org.example.project_managment_app.entities.Invitation;
import org.example.project_managment_app.entities.User;
import org.example.project_managment_app.repository.InvitationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvitationServiceImpl implements InvitationService {

    @Autowired
    private EmailService emailService;
    @Autowired
    private InvitationRepository invitationRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;

    @Override
    public void sendInvitation(String email, long projectId) throws Exception {
        User user = getUserIfExists(email);
        if (isUserAlreadyInProject(user, projectId)) {
            throw new Exception("User already in the project");
        }

        String invitationToken = UUID.randomUUID().toString();
        Invitation invitation = new Invitation();
        invitation.setEmail(email);
        invitation.setProjectId(projectId);
        invitation.setToken(invitationToken);
        invitationRepository.save(invitation);

        String invitationLink = "http://localhost:5173/accept_invitation?token="+invitationToken;
        emailService.sendEmailWithToken(email, invitationLink);
    }

    private User getUserIfExists(String email) throws Exception {
        return userService.findUserByEmail(email);
    }
    private boolean isUserAlreadyInProject(User user, long projectId) throws Exception {
        return projectService.getProjectById(projectId).getTeam().contains(user);
    }

    @Override
    public Invitation acceptInvitation(String token, long userId) throws Exception {
        Invitation invitation = invitationRepository.findByToken(token);
        if (invitation == null) {
            throw new Exception("Invalid invitation token");
        }
        return invitation;
    }

    @Override
    public String getTokenByUserMail(String email) {
        Invitation invitation = invitationRepository.findByEmail(email);
        return invitation.getToken();
    }

    @Override
    public void deleteToken(String token) {
        invitationRepository.delete(invitationRepository.findByToken(token));
    }
}
