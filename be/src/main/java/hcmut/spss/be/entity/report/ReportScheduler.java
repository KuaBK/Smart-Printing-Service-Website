package hcmut.spss.be.entity.report;

import hcmut.spss.be.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReportScheduler {

    private final ReportService reportService;

    @Autowired
    public ReportScheduler(ReportService reportService) {
        this.reportService = reportService;
    }

//    @Scheduled(fixedRate = 5000) // 5 giây
    @Scheduled(cron = "0 0 0 1 * ?")
    public void sendDailyReport() {
        String email = "loc.tran04@hcmut.edu.vn";

        System.out.println("Sending scheduled report...");
        reportService.generateReport("pdf", email);
        reportService.generateReport("excel", email);
        System.out.println("Scheduled report sent to " + email);
    }
}
