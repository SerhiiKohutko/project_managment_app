package org.example.project_managment_app.repository;

import org.example.project_managment_app.entities.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    Invitation findByEmail(String email);
    Invitation findByToken(String token);
}
