package org.example.project_managment_app.service;

import org.example.project_managment_app.entities.PlanType;
import org.example.project_managment_app.entities.Subscription;
import org.example.project_managment_app.entities.User;
import org.example.project_managment_app.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private UserService userService;

    @Override
    public Subscription createSubscription(User user) {
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusMonths(12));
        subscription.setValid(true);
        subscription.setType(PlanType.FREE);

        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription getUserSubscription(Long userId) throws Exception {
        Subscription subscription = subscriptionRepository.findByUserId(userId);
        if (!isValid(subscription)){
            subscription.setType(PlanType.FREE);
            subscription.setEndDate(LocalDate.now().plusMonths(12));
            subscription.setStartDate(LocalDate.now());
            return subscriptionRepository.save(subscription);
        }
        return subscription;
    }

    @Override
    public Subscription upgradeSubscription(Long userId, PlanType type) {
        Subscription subscription = subscriptionRepository.findByUserId(userId);
        subscription.setType(type);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(
                type.equals(PlanType.MONTHLY)
                ? LocalDate.now().plusMonths(1)
                : LocalDate.now().plusMonths(12)
        );
        subscription.setValid(true);

        return subscriptionRepository.save(subscription);
    }

    @Override
    public boolean isValid(Subscription subscription) {
        if (subscription.getType().equals(PlanType.FREE)) {
            return true;
        }

        LocalDate endDate = subscription.getEndDate();
        LocalDate now = LocalDate.now();

        return endDate.isAfter(now)|| endDate.isEqual(now);
    }
}
