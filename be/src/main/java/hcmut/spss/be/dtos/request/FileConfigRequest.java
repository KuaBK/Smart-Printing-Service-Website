package hcmut.spss.be.dtos.request;

import hcmut.spss.be.entity.fileConfig.PagesPrint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileConfigRequest {
    private String paperSize;
    private String paperRange; // pham vi in
    private String sides;
    private int numberOfCopies;
    private String layout;
    private boolean color;
    private boolean QRCode;
    private String location;
    private int pageOfSheet;
    private String margin;
    private int scale;
    private PagesPrint pagesPrint;
}
