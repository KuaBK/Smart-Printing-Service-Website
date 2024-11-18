package hcmut.spss.be.controller;


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

    @GetMapping("/vnpay")
    public ResponseEntity<?> createPayment(@RequestParam("numberPages") long numberPages) {
        PaymentResponse response = paymentService.createPaymentURL(numberPages);
        if(response.getStatus()==200){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @GetMapping("/vnpay-callback")
    public ResponseEntity<?> handleVNPayReturn(HttpServletRequest request) {
        try {
            PaymentResponse response = paymentService.handleResponse( request);
            if(response.getStatus()==200){
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

}
