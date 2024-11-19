package hcmut.spss.be.controller;


import hcmut.spss.be.dtos.request.BuyPrintingPageRequest;
import hcmut.spss.be.dtos.response.PaymentResponse;
import hcmut.spss.be.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/vnpay")
    public ResponseEntity<?> createPayment(@RequestBody BuyPrintingPageRequest request) {
        try {
            PaymentResponse response = paymentService.buyPrintingPage(request);
            if(response.getStatus()==200){
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/vnpay-callback/{codeId}")
    public ResponseEntity<?> handleVNPayReturn(@PathVariable long codeId, HttpServletRequest request) {
        try {
            PaymentResponse response = paymentService.handleResponse(codeId, request);
            if(response.getStatus()==200){
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAllPaymentOfCurrentUser() {
        try {
            return ResponseEntity.ok(paymentService.getTransactionsOfCurrentUser());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

}
