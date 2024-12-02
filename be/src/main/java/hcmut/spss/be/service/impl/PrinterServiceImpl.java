package hcmut.spss.be.service.impl;

import hcmut.spss.be.dtos.request.PrinterRequest;
import hcmut.spss.be.dtos.response.MessageResponse;
import hcmut.spss.be.dtos.response.PrinterResponse;
import hcmut.spss.be.entity.printer.Printer;
import hcmut.spss.be.entity.printer.Status;
import hcmut.spss.be.repository.PrinterRepository;
import hcmut.spss.be.service.PrinterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrinterServiceImpl implements PrinterService {

    private final PrinterRepository printerRepository;

    @Autowired
    public PrinterServiceImpl(PrinterRepository printerRepository) {
        this.printerRepository = printerRepository;
    }

    @Override
    public MessageResponse createPrinter(PrinterRequest request) {
        Printer printer = Printer.builder()
                .brand(request.getBrand())
                .model(request.getModel())
                .statusPrinter(Status.valueOf(request.getStatus()))
                .description(request.getDescription())
                .location(request.getLocation())
                .numOfPaper(request.getNumOfPaper())
                .amountOfInk(request.getAmountOfInk())
                .build();
        printerRepository.save(printer);
        return new MessageResponse("Printer created successfully");
    }

    @Override
    public PrinterResponse getPrinterById(Long id) {
        Printer printer = printerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Printer not found"));
        return PrinterResponse.toPrinterResponse(printer);
    }

    @Override
    public List<PrinterResponse> getAllPrinters() {
        List<Printer> printers = printerRepository.findAll();
        return printers.stream().map(PrinterResponse::toPrinterResponse).toList();
    }

    @Override
    public MessageResponse updatePrinter(Long id, PrinterRequest request) {
        Printer printer = printerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Printer not found"));

        if (request.getBrand() != null) {
            printer.setBrand(request.getBrand());
        }
        if (request.getModel() != null) {
            printer.setModel(request.getModel());
        }
        if (request.getStatus() != null) {
            printer.setStatusPrinter(Status.valueOf(request.getStatus()));
        }
        if (request.getDescription() != null) {
            printer.setDescription(request.getDescription());
        }
        if (request.getLocation() != null) {
            printer.setLocation(request.getLocation());
        }
        if (request.getNumOfPaper() != null) {
            printer.setNumOfPaper(request.getNumOfPaper());
        }
        if (request.getAmountOfInk() != null) {
            printer.setAmountOfInk(request.getAmountOfInk());
        }
        printerRepository.save(printer);

        return new MessageResponse("Printer updated successfully (PATCH)");
    }

    @Override
    public MessageResponse deletePrinter(Long id) {
        Printer printer = printerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Printer not found"));
        printerRepository.deleteById(id);
        return new MessageResponse("Printer deleted successfully");
    }

    @Override
    public MessageResponse togglePrinterStatus(Long id) {
        Printer printer = printerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Printer not found"));
        printer.setStatusPrinter(printer.getStatusPrinter() == Status.ENABLE ? Status.DISABLE : Status.ENABLE);
        printerRepository.save(printer);
        return new MessageResponse("Printer status toggled successfully");
    }

    @Override
    public List<PrinterResponse> getPrintersEnable() {
        return printerRepository.findAllByStatusPrinter(Status.ENABLE).stream().map(PrinterResponse::toPrinterResponse).toList();
    }
}
