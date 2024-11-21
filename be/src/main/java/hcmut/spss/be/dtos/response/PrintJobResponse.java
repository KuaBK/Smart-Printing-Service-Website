package hcmut.spss.be.dtos.response;

import hcmut.spss.be.entity.printJob.PrintJob;
import hcmut.spss.be.entity.printJob.StatusPrint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrintJobResponse {
    private Long logId;
    private LocalDateTime printTime;
    private FileConfigResponse fileConfig;
    private String statusPrint;
    private String printerBrand;
    private String location;
    private int numberPagePrint;

    public static PrintJobResponse toPrintJobResponse(PrintJob printJob) {
        return PrintJobResponse.builder()
                .logId(printJob.getJobId())
                .printTime(printJob.getJobStartTime())
                .statusPrint(printJob.getStatusPrint().name())
                .fileConfig(FileConfigResponse.toFileConfigResponse(printJob.getFileConfig()))
                .printerBrand(printJob.getPrinter().getBrand())
                .location(printJob.getPrinter().getLocation())
                .numberPagePrint(printJob.getNumberPagePrint())
                .build();
    }
}
