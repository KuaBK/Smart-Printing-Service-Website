package hcmut.spss.be.controller;

import hcmut.spss.be.dtos.request.DiscountRequest;
import hcmut.spss.be.service.*;
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

    @Autowired
    private PrintJobService printJobService;

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private ReportService reportService;

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

    @GetMapping("/print-logs")
    public ResponseEntity<?> getAllPrintLogs(@RequestParam(defaultValue = "0") Boolean searchFlag) {
        try {
            if (searchFlag == Boolean.TRUE) {
                return ResponseEntity.ok(printJobService.getAllPrintJobInWeek());
            }
            return ResponseEntity.ok(printJobService.getAllPrintJobs());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }

    @GetMapping("/get-statistic")
    public ResponseEntity<?> getStatistic() {
        try {
            return ResponseEntity.ok(statisticService.getStatistic());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/daily-report")
    public ResponseEntity<?> getDailyReport(){
        try {
            return ResponseEntity.ok(reportService.getReport());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
