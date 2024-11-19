package hcmut.spss.be.dtos.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DiscountRequest {
    private String semester;
    private int pagesFree;
    private LocalDate startDate;
    private LocalDate expirationDate;
    boolean isAll;
}
