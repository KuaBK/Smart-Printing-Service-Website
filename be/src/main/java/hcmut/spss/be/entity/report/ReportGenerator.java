package hcmut.spss.be.entity.report;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import hcmut.spss.be.entity.payment.Payment;
import hcmut.spss.be.repository.PaymentRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class    ReportGenerator {
    private final PaymentRepository paymentRepository;
    private final File reportDirectory;

    public ReportGenerator(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
        this.reportDirectory = new File("report");

        if (!reportDirectory.exists()) {
            boolean created = reportDirectory.mkdirs();
            if (created) {
                System.out.println("Report directory created successfully.");
            } else {
                System.out.println("Failed to create report directory.");
            }
        }
    }

    public File generatePdfReport() {
        System.out.println("Generating PDF report...");
        try {
//            File reportFile = new File("payment_report.pdf");
            File reportFile = new File(reportDirectory, "payment_report.pdf");
            PdfWriter writer = new PdfWriter(reportFile);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            document.add(new Paragraph("Payment Report").setFontSize(18));
            List<Payment> payments = paymentRepository.findAll();
            for (Payment payment : payments) {
                document.add(new Paragraph("Payment ID: " + payment.getPaymentId()));
                document.add(new Paragraph("Amount Paid: " + payment.getAmountPaid()));
                document.add(new Paragraph("Payment Method: " + payment.getPaymentMethod()));
                document.add(new Paragraph("Number of Pages Bought: " + payment.getNumberOfPagesBought()));
                document.add(new Paragraph("Payment Date: " + payment.getPaymentDate()));
                document.add(new Paragraph("Student: " + payment.getStudent().getName()));
                document.add(new Paragraph("Discount: " + payment.getDiscount()));
                document.add(new Paragraph("-------------"));
            }

            document.close();
            System.out.println("PDF report generated successfully.");
            return reportFile;
        } catch (IOException e) {
            throw new RuntimeException("Error generating PDF report", e);
        }
    }

    public File generateExcelReport() {
        try {
//            File reportFile = new File("payment_report.xlsx");
            File reportFile = new File(reportDirectory, "payment_report.xlsx");
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Payment Report");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Payment ID");
            headerRow.createCell(1).setCellValue("Amount Paid");
            headerRow.createCell(2).setCellValue("Payment Method");
            headerRow.createCell(3).setCellValue("Number of Pages Bought");
            headerRow.createCell(4).setCellValue("Payment Date");
            headerRow.createCell(5).setCellValue("Student");
            headerRow.createCell(6).setCellValue("Discount");

            List<Payment> payments = paymentRepository.findAll();
            int rowNum = 1;
            for (Payment payment : payments) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(payment.getPaymentId());
                row.createCell(1).setCellValue(payment.getAmountPaid());
                row.createCell(2).setCellValue(payment.getPaymentMethod().toString());
                row.createCell(3).setCellValue(payment.getNumberOfPagesBought());
                row.createCell(4).setCellValue(payment.getPaymentDate().toString());
                row.createCell(5).setCellValue(payment.getStudent().getName());
                row.createCell(6).setCellValue(payment.getDiscount() != null ? payment.getDiscount().toString() : "No Discount");
            }

            FileOutputStream fileOut = new FileOutputStream(reportFile);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();

            System.out.println("Excel report generated successfully.");
            return reportFile;
        } catch (IOException e) {
            throw new RuntimeException("Error generating Excel report", e);
        }
    }
}

