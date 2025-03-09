package org.example.project_managment_app.controller;

import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.example.project_managment_app.entities.PaymentStatus;
import org.example.project_managment_app.entities.PlanType;
import org.example.project_managment_app.entities.User;
import org.example.project_managment_app.response.PaymentLinkResponse;
import org.example.project_managment_app.service.PaymentService;
import org.example.project_managment_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private UserService userService;
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/{planType}")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
            @PathVariable PlanType planType,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        int amount = 15;

        if (planType.equals(PlanType.ANNUALLY)) {
            amount = 15 * 12;
            amount = (int) (amount*0.7);
        } else if (planType.equals(PlanType.MONTHLY)) {
            amount = 20 * 100;
        }

        try {
            SessionCreateParams params = SessionCreateParams.builder()
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:5173/upgrade_plan/success?plan_type=" + planType + "&payment_id={CHECKOUT_SESSION_ID}")
                    .setCancelUrl("http://localhost:3000/cancel")
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency("usd")
                                                    .setUnitAmount((long) amount)
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName("Продукт X")
                                                                    .build()
                                                    )
                                                    .build()
                                    )
                                    .build()
                    )
                    .build();

            Session session = Session.create(params);

            PaymentLinkResponse response = new PaymentLinkResponse();
            response.setPayment_link_url(session.getUrl());
            response.setPayment_link_id(session.getId());

            paymentService.createPayment(session.getId(), PaymentStatus.PENDING);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании сессии оплаты", e);
        }
    }





}
