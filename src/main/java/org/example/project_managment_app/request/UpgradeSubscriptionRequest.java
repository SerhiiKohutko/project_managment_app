package org.example.project_managment_app.request;

import lombok.Data;
import org.example.project_managment_app.entities.PlanType;

@Data
public class UpgradeSubscriptionRequest {
    private String paymentId;
    private PlanType planType;
}
