package hcmut.spss.be.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenueResponse {
    private int year;
    private int month;
    private double revenue;
}
