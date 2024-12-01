package hcmut.spss.be.service;


import hcmut.spss.be.dtos.response.ApiResponse;

public interface ReportService {
    void generateReport(String format, String recipientEmail);

    ApiResponse<?> getReport();
}
