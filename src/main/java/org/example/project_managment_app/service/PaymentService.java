package org.example.project_managment_app.service;

import org.example.project_managment_app.entities.Payment;
import org.example.project_managment_app.entities.PaymentStatus;

public interface PaymentService {
    boolean isPaymentSucceed(String paymentId) throws Exception;
    Payment createPayment(String paymentId, PaymentStatus status);
}
