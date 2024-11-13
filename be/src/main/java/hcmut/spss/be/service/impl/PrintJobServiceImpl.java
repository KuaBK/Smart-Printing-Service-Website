//package hcmut.spss.be.service.impl;
//
//import hcmut.spss.be.dtos.request.PrintJobRequest;
//import hcmut.spss.be.dtos.response.MessageResponse;
//import hcmut.spss.be.dtos.response.PrintJobResponse;
//import hcmut.spss.be.entity.document.Document;
//import hcmut.spss.be.entity.fileConfig.FileConfig;
//import hcmut.spss.be.entity.printJob.PrintJob;
//import hcmut.spss.be.entity.printJob.StatusPrint;
//import hcmut.spss.be.entity.printer.Printer;
//import hcmut.spss.be.entity.user.User;
//import hcmut.spss.be.repository.DocumentRepository;
//import hcmut.spss.be.repository.FileConfigRepository;
//import hcmut.spss.be.repository.PrintJobRepository;
//import hcmut.spss.be.repository.PrinterRepository;
//import hcmut.spss.be.repository.UserRepository;
//import hcmut.spss.be.service.PrintJobService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class PrintJobServiceImpl implements PrintJobService {
//
//    private final PrintJobRepository printJobRepository;
//    private final PrinterRepository printerRepository;
//    private final UserRepository userRepository;
//    private final FileConfigRepository fileConfigRepository;
//    private final DocumentRepository documentRepository;
//
//    @Autowired
//    public PrintJobServiceImpl(PrintJobRepository printJobRepository, PrinterRepository printerRepository,
//                               UserRepository userRepository, FileConfigRepository fileConfigRepository,
//                               DocumentRepository documentRepository) {
//        this.printJobRepository = printJobRepository;
//        this.printerRepository = printerRepository;
//        this.userRepository = userRepository;
//        this.fileConfigRepository = fileConfigRepository;
//        this.documentRepository = documentRepository;
//    }
//
//    @Override
//    public MessageResponse createPrintJob(PrintJobRequest request) {
//        Printer printer = printerRepository.findById(request.getPrinterId())
//                .orElseThrow(() -> new RuntimeException("Printer not found"));
//        User student = userRepository.findById(request.getStudentId())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        FileConfig fileConfig = fileConfigRepository.findById(request.getFileConfigId())
//                .orElseThrow(() -> new RuntimeException("FileConfig not found"));
//        Document document = documentRepository.findById(request.getDocumentId())
//                .orElseThrow(() -> new RuntimeException("Document not found"));
//
//        PrintJob printJob = PrintJob.builder()
//                .printer(printer)
//                .student(student)
//                .fileConfig(fileConfig)
//                .document(document)
//                .statusPrint(StatusPrint.valueOf(request.getStatusPrint()))
//                .build();
//        printJobRepository.save(printJob);
//
//        return new MessageResponse("PrintJob created successfully");
//    }
//
//    @Override
//    public PrintJobResponse getPrintJobById(Long id) {
//        PrintJob printJob = printJobRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("PrintJob not found"));
//        return toPrintJobResponse(printJob);
//    }
//
//    @Override
//    public List<PrintJobResponse> getAllPrintJobsOfCurrentUser() {
//        Long currentUserId = authUtil.loggedInUserId();
//        List<PrintJob> printJobs = printJobRepository.findAllByStudentId(currentUserId);
//        return printJobs.stream().map(this::toPrintJobResponse).toList();
//    }
//
//    @Override
//    public MessageResponse updatePrintJobStatus(Long id, String statusPrint) {
//        PrintJob printJob = printJobRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("PrintJob not found"));
//        printJob.setStatusPrint(StatusPrint.valueOf(statusPrint));
//        printJobRepository.save(printJob);
//        return new MessageResponse("PrintJob status updated successfully");
//    }
//
//    @Override
//    public MessageResponse deletePrintJob(Long id) {
//        PrintJob printJob = printJobRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("PrintJob not found"));
//        printJobRepository.deleteById(id);
//        return new MessageResponse("PrintJob deleted successfully");
//    }
//
//    private PrintJobResponse toPrintJobResponse(PrintJob printJob) {
//        return PrintJobResponse.builder()
//                .jobId(printJob.getJobId())
//                .jobStartTime(printJob.getJobStartTime())
//                .statusPrint(printJob.getStatusPrint())
//                .printerId(printJob.getPrinter().getPrinterId())
//                .studentId(printJob.getStudent().getUserId())
//                .fileConfigId(printJob.getFileConfig().getId())
//                .documentId(printJob.getDocument().getDocumentId())
//                .build();
//    }
//}

package hcmut.spss.be.service.impl;

import hcmut.spss.be.dtos.response.PrintJobResponse;
import hcmut.spss.be.dtos.response.MessageResponse;
import hcmut.spss.be.entity.printJob.PrintJob;
import hcmut.spss.be.entity.printJob.StatusPrint;
import hcmut.spss.be.repository.PrintJobRepository;
import hcmut.spss.be.service.PrintJobService;
import hcmut.spss.be.utils.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrintJobServiceImpl implements PrintJobService {

    @Autowired
    private PrintJobRepository printJobRepository;

    @Autowired
    private AuthUtil authUtil;

    @Override
    public List<PrintJobResponse> getAllPrintJobsOfCurrentUser() {
        Long currentUserId = authUtil.loggedInUserId();
        List<PrintJob> printJobs = printJobRepository.findAllByStudent_UserId(currentUserId);

        return printJobs.stream()
                .map(this::toPrintJobResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MessageResponse createPrintJob(PrintJob printJob) {
        Long currentUserId = authUtil.loggedInUserId();
        printJob.setStudent(authUtil.loggedInUser());
        printJob.setStatusPrint(StatusPrint.WAITING);
        printJobRepository.save(printJob);
        return new MessageResponse("Print job created successfully");
    }

    @Override
    public MessageResponse updatePrintJobStatus(Long jobId, StatusPrint statusPrint) {
        PrintJob printJob = printJobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Print job not found"));

        printJob.setStatusPrint(statusPrint);
        printJobRepository.save(printJob);
        return new MessageResponse("Print job status updated successfully");
    }

    @Override
    public MessageResponse deletePrintJob(Long jobId) {
        PrintJob printJob = printJobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Print job not found"));

        printJobRepository.deleteById(jobId);
        return new MessageResponse("Print job deleted successfully");
    }

    private PrintJobResponse toPrintJobResponse(PrintJob printJob) {
        return new PrintJobResponse(
                printJob.getJobId(),
                printJob.getJobStartTime(),
                printJob.getStatusPrint().name(),
                printJob.getPrinter().getBrand(),
                printJob.getFileConfig().getPaperRange()
        );
    }

    public PrintJobResponse getPrintJobById(Long jobId) {
        PrintJob printJob = printJobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Print job not found"));
        return toPrintJobResponse(printJob);
    }
}

