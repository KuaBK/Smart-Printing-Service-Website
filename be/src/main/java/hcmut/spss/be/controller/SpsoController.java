package hcmut.spss.be.controller;

import hcmut.spss.be.dtos.request.DiscountRequest;
import hcmut.spss.be.service.DiscountService;
import hcmut.spss.be.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/spso")
public class SpsoController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private DiscountService discountService;

    @GetMapping("/transactions")
    public ResponseEntity<?> getTransactions() {
        try {
            return ResponseEntity.ok(paymentService.getAllTransactions());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }

    @PostMapping("/make-discount")
    public ResponseEntity<?> makeDiscount(@RequestBody DiscountRequest request) {
        try {
            return ResponseEntity.ok(discountService.makeDiscount(request));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }

    @GetMapping("/discounts")
    public ResponseEntity<?> getDiscounts() {
        try {
            return ResponseEntity.ok(discountService.getAllDiscounts());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }
}
