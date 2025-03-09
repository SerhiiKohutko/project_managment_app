package org.example.project_managment_app.repository;

import org.example.project_managment_app.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Subscription findByUserId(long userId);
}
