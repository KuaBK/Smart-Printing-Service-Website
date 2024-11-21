package hcmut.spss.be.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrintJobRequest {
    private Long printerId;
    private Long fileConfigId;
}
