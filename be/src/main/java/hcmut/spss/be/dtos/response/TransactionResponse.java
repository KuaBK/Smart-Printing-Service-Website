package hcmut.spss.be.dtos.response;

import hcmut.spss.be.entity.payment.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private Long id;
    private String studentName;
    private String mssv;
    private double amount;
    private String method;
    private int numberOfPages;
    private LocalDateTime paidTime;
    private String discountCode;

    public static TransactionResponse toTransactionResponse(Payment payment) {
        return TransactionResponse.builder()
                .id(payment.getPaymentId())
                .studentName(payment.getStudent().getName())
                .mssv(payment.getStudent().getMssv())
                .amount(payment.getAmountPaid())
                .method(payment.getPaymentMethod().name())
                .numberOfPages(payment.getNumberOfPagesBought())
                .paidTime(payment.getPaymentDate())
                .discountCode(payment.getDiscount()!=null? payment.getDiscount().getDiscountCode() : "NaN")
                .build();
    }
}
