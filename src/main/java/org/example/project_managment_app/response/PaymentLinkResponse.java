package org.example.project_managment_app.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentLinkResponse {
    private String payment_link_url;
    private String payment_link_id;
}
