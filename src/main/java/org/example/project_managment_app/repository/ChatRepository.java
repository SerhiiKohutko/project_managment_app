package org.example.project_managment_app.repository;

import org.example.project_managment_app.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
