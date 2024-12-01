package hcmut.spss.be.entity.report;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyReport {
    private String date;
    private Integer numOfPayments;
    private Double amountPaid;
    private Integer numPagesBought;
    private Integer totalPagesPrinted;
    private Integer totalPrints;
    private Integer numAccess;

    // Constructor
    public DailyReport(String date) {
        this.date = date;
        this.numOfPayments = 0;
        this.amountPaid = 0.0;
        this.numPagesBought = 0;
        this.totalPagesPrinted = 0;
        this.totalPrints = 0;
        this.numAccess = 0;
    }
}
