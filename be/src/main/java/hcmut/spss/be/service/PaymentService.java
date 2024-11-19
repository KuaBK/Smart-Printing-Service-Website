package hcmut.spss.be.service;

import hcmut.spss.be.dtos.request.BuyPrintingPageRequest;
import hcmut.spss.be.dtos.response.PaymentResponse;
import hcmut.spss.be.dtos.response.TransactionResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface PaymentService {


    PaymentResponse buyPrintingPage(BuyPrintingPageRequest request);

    PaymentResponse handleResponse(long codeId, HttpServletRequest request);

    List<TransactionResponse> getTransactionsOfCurrentUser(); //user

    List<TransactionResponse> getAllTransactions(); //admin
}
