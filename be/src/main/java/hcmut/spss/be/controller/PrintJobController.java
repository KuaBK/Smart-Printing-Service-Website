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

import java.util.List;

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

    @PostMapping("/create")
    public ResponseEntity<?> createPrintJob(@RequestBody PrintJobRequest request) {
        try {
            return ResponseEntity.ok(printJobService.makeLog(request.getFileConfigId(), request.getPrinterId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping("/public/print-by-code")
    public ResponseEntity<?> createLogByCode(@RequestBody PrintJobRequest request) {
        try {
            return ResponseEntity.ok(printJobService.makeLog(request.getFileConfigId(), request.getPrinterId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPrintJobById(@PathVariable Long id) {
        try {
            PrintJobResponse response = printJobService.getPrintJobById(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/current-user")
    public ResponseEntity<?> getAllPrintJobsOfCurrentUser() {
        try {
            List<PrintJobResponse> responses = printJobService.getAllPrintJobsOfCurrentUser();
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<?> updatePrintJobStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            StatusPrint statusPrint = StatusPrint.valueOf(status.toUpperCase());
            MessageResponse response = printJobService.updatePrintJobStatus(id, statusPrint);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid status value"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePrintJob(@PathVariable Long id) {
        try {
            MessageResponse response = printJobService.deletePrintJob(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
