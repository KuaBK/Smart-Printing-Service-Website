package hcmut.spss.be.controller;

import hcmut.spss.be.dtos.request.PrintJobRequest;
import hcmut.spss.be.dtos.response.PrintJobResponse;
import hcmut.spss.be.dtos.response.MessageResponse;
import hcmut.spss.be.entity.printJob.PrintJob;
import hcmut.spss.be.entity.printJob.StatusPrint;
import hcmut.spss.be.entity.printer.Printer;
import hcmut.spss.be.entity.user.User;
import hcmut.spss.be.entity.fileConfig.FileConfig;
import hcmut.spss.be.entity.document.Document;
import hcmut.spss.be.service.PrintJobService;
import hcmut.spss.be.repository.PrinterRepository;
import hcmut.spss.be.repository.UserRepository;
import hcmut.spss.be.repository.FileConfigRepository;
import hcmut.spss.be.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/print-jobs")
public class PrintJobController {

    private final PrintJobService printJobService;
    private final PrinterRepository printerRepository;
    private final UserRepository userRepository;
    private final FileConfigRepository fileConfigRepository;
    private final DocumentRepository documentRepository;

    @Autowired
    public PrintJobController(PrintJobService printJobService,
                              PrinterRepository printerRepository,
                              UserRepository userRepository,
                              FileConfigRepository fileConfigRepository,
                              DocumentRepository documentRepository) {
        this.printJobService = printJobService;
        this.printerRepository = printerRepository;
        this.userRepository = userRepository;
        this.fileConfigRepository = fileConfigRepository;
        this.documentRepository = documentRepository;
    }

    // Tạo PrintJob
    @PostMapping("/create")
    public ResponseEntity<?> createPrintJob(@RequestBody PrintJobRequest request) {
        try {
            PrintJob printJob = convertToPrintJob(request);
            return ResponseEntity.ok(printJobService.createPrintJob(printJob));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    // Lấy thông tin PrintJob theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPrintJobById(@PathVariable Long id) {
        try {
            PrintJobResponse response = printJobService.getPrintJobById(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    // Cập nhật trạng thái PrintJob
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updatePrintJobStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            StatusPrint statusPrint = StatusPrint.valueOf(status.toUpperCase()); // Chuyển đổi từ String thành StatusPrint enum
            return ResponseEntity.ok(printJobService.updatePrintJobStatus(id, statusPrint));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid status value"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    private PrintJob convertToPrintJob(PrintJobRequest request) {
        // Lấy các đối tượng từ ID
        Printer printer = printerRepository.findById(request.getPrinterId())
                .orElseThrow(() -> new RuntimeException("Printer not found"));
        User student = userRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        FileConfig fileConfig = fileConfigRepository.findById(request.getFileConfigId())
                .orElseThrow(() -> new RuntimeException("FileConfig not found"));
        Document document = documentRepository.findById(request.getDocumentId())
                .orElseThrow(() -> new RuntimeException("Document not found"));

        return PrintJob.builder()
                .statusPrint(StatusPrint.WAITING)
                .printer(printer)
                .student(student)
                .fileConfig(fileConfig)
                .document(document)
                .build();
    }
}
