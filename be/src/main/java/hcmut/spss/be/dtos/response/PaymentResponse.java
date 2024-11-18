package hcmut.spss.be.dtos.response;

import hcmut.spss.be.entity.payment.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private int status;
    private String message;
    private String url;
}
