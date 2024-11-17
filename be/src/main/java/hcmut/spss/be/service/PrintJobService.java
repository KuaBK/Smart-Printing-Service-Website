package hcmut.spss.be.service;

import hcmut.spss.be.dtos.response.PrintJobResponse;
import hcmut.spss.be.dtos.response.MessageResponse;
import hcmut.spss.be.entity.printJob.PrintJob;
import hcmut.spss.be.entity.printJob.StatusPrint;

import java.util.List;

public interface PrintJobService {

    List<PrintJobResponse> getAllPrintJobsOfCurrentUser();

    MessageResponse createPrintJob(PrintJob printJob);

    MessageResponse updatePrintJobStatus(Long jobId, StatusPrint statusPrint);

    MessageResponse deletePrintJob(Long jobId);

    PrintJobResponse getPrintJobById(Long jobId);
}
