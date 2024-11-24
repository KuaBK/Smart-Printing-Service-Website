package hcmut.spss.be.controller;

import hcmut.spss.be.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/send/report")
    public ResponseEntity<String> sendReport(
            @RequestParam String format,
            @RequestParam String email) {
        try {
            reportService.generateReport(format, email);
            return ResponseEntity.ok("Report sent successfully to " + email);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send report: " + e.getMessage());
        }
    }
}
