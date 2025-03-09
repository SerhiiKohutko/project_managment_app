package org.example.project_managment_app.service;

import org.example.project_managment_app.entities.PlanType;
import org.example.project_managment_app.entities.Subscription;
import org.example.project_managment_app.entities.User;

public interface SubscriptionService {

    Subscription createSubscription(User user);
    Subscription getUserSubscription(Long userId) throws Exception;
    Subscription upgradeSubscription(Long userId, PlanType type) throws Exception;
    boolean isValid(Subscription subscription);
}
