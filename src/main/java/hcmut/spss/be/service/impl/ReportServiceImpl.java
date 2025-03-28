package hcmut.spss.be.service.impl;

import hcmut.spss.be.dtos.response.ApiResponse;
import hcmut.spss.be.entity.report.DailyReport;
import hcmut.spss.be.entity.report.ReportGenerator;
import hcmut.spss.be.repository.PaymentRepository;
import hcmut.spss.be.repository.PrintJobRepository;
import hcmut.spss.be.repository.UserVisitRepository;
import hcmut.spss.be.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.*;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportGenerator reportGenerator;
    private final EmailServiceImpl emailService;

    @Autowired
    private UserVisitRepository visitRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private PrintJobRepository printJobRepository;

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

    @Override
    public ApiResponse<?> getReport() {
        try {
            Map<String, DailyReport> dailyReport = new TreeMap<>(); // TreeMap để giữ thứ tự theo ngày.

            // Xử lý danh sách từ countVisitsByDay
            List<Object[]> visitData = visitRepository.countVisitsByDay();
            for (Object[] row : visitData) {
                String date = row[0].toString(); // Chuyển ngày sang String
                Integer visitCount = ((Number) row[1]).intValue();

                // Nếu ngày chưa có trong Map, khởi tạo với giá trị mặc định
                dailyReport.putIfAbsent(date, new DailyReport(date));
                DailyReport report = dailyReport.get(date);
                report.setNumAccess(visitCount);
            }

            // Xử lý danh sách từ countTodayPayments
            List<Object[]> paymentData = paymentRepository.countTodayPayments();
            for (Object[] row : paymentData) {
                String date = row[0].toString();
                Integer numOfPayments = ((Number) row[1]).intValue();
                Double amountPaid = ((Number) row[2]).doubleValue();
                Integer numPagesBought = ((Number) row[3]).intValue();

                dailyReport.putIfAbsent(date, new DailyReport(date));
                DailyReport report = dailyReport.get(date);
                report.setNumOfPayments(numOfPayments);
                report.setAmountPaid(amountPaid);
                report.setNumPagesBought(numPagesBought);
            }

            // Xử lý danh sách từ countPrintJobByDay
            List<Object[]> printJobData = printJobRepository.countPrintJobByDay();
            for (Object[] row : printJobData) {
                String date = row[0].toString();
                Integer totalPages = ((Number) row[1]).intValue();
                Integer totalPrints = ((Number) row[2]).intValue();

                dailyReport.putIfAbsent(date, new DailyReport(date));
                DailyReport report = dailyReport.get(date);
                report.setTotalPagesPrinted(totalPages);
                report.setTotalPrints(totalPrints);
            }

            List<DailyReport> dailyReports = new ArrayList<>(dailyReport.values());
            Map<String, Object> response = new HashMap<>();
            response.put("report", dailyReports);
            response.put("url-download", "http://localhost:8080/api/reports/download");

            return new ApiResponse<>(200, "success", response);
        }catch (Exception e) {
            return new ApiResponse<>(500, e.getMessage(), null);
        }
    }
}

