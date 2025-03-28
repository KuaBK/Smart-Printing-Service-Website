package hcmut.spss.be.controller;


import hcmut.spss.be.dtos.request.BuyPrintingPageRequest;
import hcmut.spss.be.dtos.response.ApiResponse;
import hcmut.spss.be.dtos.response.DiscountResponse;
import hcmut.spss.be.dtos.response.PaymentResponse;
import hcmut.spss.be.service.DiscountService;
import hcmut.spss.be.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private DiscountService discountService;

    @GetMapping("/discount")
    public ResponseEntity<?> getDiscount(@RequestParam String code){
        try {
            return ResponseEntity.ok(new ApiResponse<>(200, "success", discountService.getDiscountByCode(code)));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(400, e.getMessage(), null));
        }
    }

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

    @GetMapping("/vnpay-callback/{id}")
    public ResponseEntity<?> handleVNPayReturn(@PathVariable String id, HttpServletRequest request) {
//        try {
//            PaymentResponse response = paymentService.handleResponse(id, request);
//            if(response.getStatus()==200){
//                return ResponseEntity.ok(response);
//            }
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
//        }
        try {
            PaymentResponse response = paymentService.handleResponse(id, request);

            if (response.getStatus() == 200) {
                // Redirect to localhost:5173 after successful payment
                return ResponseEntity.status(HttpStatus.FOUND).header("Location", "http://localhost:5173").build();
            }

            // Redirect to localhost:5173 on failure
            return ResponseEntity.status(HttpStatus.FOUND).header("Location", "http://localhost:5173").build();
        } catch (Exception e) {
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
