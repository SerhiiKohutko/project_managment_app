package org.example.project_managment_app.controller;

import org.example.project_managment_app.entities.Subscription;
import org.example.project_managment_app.entities.User;
import org.example.project_managment_app.request.UpgradeSubscriptionRequest;
import org.example.project_managment_app.service.PaymentService;
import org.example.project_managment_app.service.SubscriptionService;
import org.example.project_managment_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ResponseEntity<Subscription> getUserSubscription(@RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);

        Subscription subscription = subscriptionService.getUserSubscription(user.getId());

        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }

    @PatchMapping("/upgrade")
    public ResponseEntity<Subscription> upgradeSubscription(@RequestHeader("Authorization") String jwt,
                                                            @RequestBody UpgradeSubscriptionRequest req) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);

        if (!paymentService.isPaymentSucceed(req.getPaymentId())){
            throw new Exception("Payment failed");
        }

        Subscription subscription = subscriptionService.upgradeSubscription(user.getId(), req.getPlanType());

        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }


}
