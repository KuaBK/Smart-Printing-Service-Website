package hcmut.spss.be.controller;

import hcmut.spss.be.dtos.response.ApiResponse;
import hcmut.spss.be.entity.report.DailyReport;
import hcmut.spss.be.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class CsvDownloadController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadCsv() {
        try {
            // Lấy danh sách báo cáo
            ApiResponse<?> apiResponse = reportService.getReport();
            Map<String, Object> response = (Map<String, Object>) apiResponse.getData();

            // Lấy danh sách DailyReport
            @SuppressWarnings("unchecked") // Chỉ định rõ kiểu dữ liệu
            List<DailyReport> dailyReports = (List<DailyReport>) response.get("report");

            // Kiểm tra kết quả
            if (dailyReports == null) {
                throw new RuntimeException("No report found");
            }


            // Tạo CSV trong bộ nhớ
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintWriter writer = new PrintWriter(outputStream);

            // Tiêu đề cột - mỗi cột sẽ nằm trong ô riêng biệt
            writer.print("Date,");
            writer.print("Num Access,");
            writer.print("Num Of Payments,");
            writer.print("Amount Paid,");
            writer.print("Num Pages Bought,");
            writer.print("Total Pages Printed,");
            writer.println("Total Prints");

            // Ghi dữ liệu từng dòng
            for (DailyReport report : dailyReports) {
                writer.print(report.getDate() != null ? report.getDate() : "");
                writer.print(",");
                writer.print(report.getNumAccess() != null ? report.getNumAccess() : 0);
                writer.print(",");
                writer.print(report.getNumOfPayments() != null ? report.getNumOfPayments() : 0);
                writer.print(",");
                writer.print(report.getAmountPaid() != null ? report.getAmountPaid() : 0.0);
                writer.print(",");
                writer.print(report.getNumPagesBought() != null ? report.getNumPagesBought() : 0);
                writer.print(",");
                writer.print(report.getTotalPagesPrinted() != null ? report.getTotalPagesPrinted() : 0);
                writer.print(",");
                writer.println(report.getTotalPrints() != null ? report.getTotalPrints() : 0);
            }

            writer.flush();

            // Chuyển nội dung CSV thành mảng byte
            byte[] csvContent = outputStream.toByteArray();

            // Đóng writer
            writer.close();

            // Trả về file CSV
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=daily_reports.csv")
                    .contentType(MediaType.parseMediaType("text/csv"))
                    .body(csvContent);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}