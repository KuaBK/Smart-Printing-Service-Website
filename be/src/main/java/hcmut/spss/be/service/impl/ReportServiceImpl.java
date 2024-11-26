package hcmut.spss.be.service.impl;

import hcmut.spss.be.entity.report.ReportGenerator;
import hcmut.spss.be.repository.PaymentRepository;
import hcmut.spss.be.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportGenerator reportGenerator;
    private final EmailServiceImpl emailService;

    @Autowired
    public ReportServiceImpl(PaymentRepository paymentRepository, EmailServiceImpl emailService) {
        this.reportGenerator = new ReportGenerator(paymentRepository);
        this.emailService = emailService;
    }

    @Override
    public void generateReport(String format, String recipientEmail) {
        File reportFile;
        if ("pdf".equalsIgnoreCase(format)) {
            reportFile = reportGenerator.generatePdfReport();
        } else if ("excel".equalsIgnoreCase(format)) {
            reportFile = reportGenerator.generateExcelReport();
        } else {
            throw new IllegalArgumentException("Unsupported format: " + format);
        }
        // Gửi báo cáo qua email
        String subject = "Monthly Report";
        String messageBody = "Please check the monthly report of SPSS";
        emailService.sendEmailWithAttachment(recipientEmail, subject, messageBody, reportFile);
    }
}

