package hcmut.spss.be.dtos.response;

import hcmut.spss.be.entity.printer.Printer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrinterResponse {
    private Long printerId;
    private String brand;
    private String model;
    private String status;
    private String description;
    private String location;
    private Integer numOfPaper;
    private Integer amountOfInk;

    public static PrinterResponse toPrinterResponse(Printer printer) {
        return PrinterResponse.builder()
                .printerId(printer.getPrinterId())
                .brand(printer.getBrand())
                .model(printer.getModel())
                .status(printer.getStatusPrinter().name())
                .description(printer.getDescription())
                .location(printer.getLocation())
                .numOfPaper(printer.getNumOfPaper())
                .amountOfInk(printer.getAmountOfInk())
                .build();
    }
}
