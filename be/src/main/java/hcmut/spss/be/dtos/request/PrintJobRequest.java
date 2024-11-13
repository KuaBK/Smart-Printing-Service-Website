package hcmut.spss.be.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrintJobRequest {
    private Long printerId;
    private Long studentId;
    private Long fileConfigId;
    private Long documentId;
    private String statusPrint;
}
