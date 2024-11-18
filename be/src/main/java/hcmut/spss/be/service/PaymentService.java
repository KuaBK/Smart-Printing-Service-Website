package hcmut.spss.be.service;

import hcmut.spss.be.dtos.response.PaymentResponse;
import hcmut.spss.be.entity.payment.Payment;
import jakarta.servlet.http.HttpServletRequest;

public interface PaymentService {

    PaymentResponse createPaymentURL(long numberPages);

    PaymentResponse handleResponse(HttpServletRequest request);
}
