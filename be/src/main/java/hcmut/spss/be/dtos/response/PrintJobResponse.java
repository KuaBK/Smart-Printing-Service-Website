package hcmut.spss.be.dtos.response;

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
    private Long jobId;
    private LocalDateTime jobStartTime;
    private String statusPrint;
    private String printerBrand;
    private String paperRange;
}
