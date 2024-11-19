package hcmut.spss.be.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuyPrintingPageRequest {
    private int page;
    private String discountCode;
}
