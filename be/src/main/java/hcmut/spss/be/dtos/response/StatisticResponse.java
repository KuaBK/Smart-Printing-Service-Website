package hcmut.spss.be.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatisticResponse {
    Map<String, Integer> access;
    Map<String, Integer> pagePrinted;
    Map<String, Integer> pageSold;
    List<PrintJobStats> printing;
    List<RevenueResponse> revenues;
}
