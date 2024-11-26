package hcmut.spss.be.service.impl;

import hcmut.spss.be.dtos.request.FileConfigRequest;
import hcmut.spss.be.dtos.response.ApiResponse;
import hcmut.spss.be.dtos.response.MessageResponse;
import hcmut.spss.be.dtos.response.FileConfigResponse;
import hcmut.spss.be.dtos.response.PrinterResponse;
import hcmut.spss.be.entity.codePrint.CodePrint;
import hcmut.spss.be.entity.document.Document;
import hcmut.spss.be.entity.fileConfig.*;
import hcmut.spss.be.entity.printer.Printer;
import hcmut.spss.be.entity.printer.Status;
import hcmut.spss.be.entity.user.User;
import hcmut.spss.be.repository.CodePrintRepository;
import hcmut.spss.be.repository.DocumentRepository;
import hcmut.spss.be.repository.FileConfigRepository;
import hcmut.spss.be.repository.PrinterRepository;
import hcmut.spss.be.service.FileConfigService;
import hcmut.spss.be.utils.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class FileConfigServiceImpl implements FileConfigService {

    private final FileConfigRepository fileConfigRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private CodePrintRepository codePrintRepository;

    @Autowired
    private PrinterRepository printerRepository;

    @Autowired
    public FileConfigServiceImpl(FileConfigRepository fileConfigRepository) {
        this.fileConfigRepository = fileConfigRepository;
    }

    @Override
    public ApiResponse<?> createFileConfig(FileConfigRequest request, Long id) {
        // find document to config
        Document document = documentRepository.findById(id).orElseThrow(() -> new RuntimeException("Document not found"));

        User student = authUtil.loggedInUser();

        // check generateCode;
        CodePrint codePrint =  request.isQrCode()? CodePrint.builder()
                .generatedCode(generateCode())
                .codeStartDate(LocalDateTime.now())
                .codeEndDate(LocalDateTime.now().plusDays(1)).build() : null;

        // create instance file config from request
        FileConfig fileConfig = FileConfig.builder()
                .paperSize(PaperSize.valueOf(request.getPaperSize()))
                .paperRange(request.getPaperRange())
                .sides(Sides.valueOf(request.getSides()))
                .numberOfCopies(request.getNumberOfCopies())
                .layout(Layout.valueOf(request.getLayout()))
                .color(request.isColor())
                .QRCode(request.isQrCode())
                .pageOfSheet(request.getPageOfSheet())
                .margin(request.getMargin())
                .scale(request.getScale())
                .document(document)
                .student(student)
                .build();

        fileConfigRepository.save(fileConfig);
        FileConfigResponse response = FileConfigResponse.toFileConfigResponse(fileConfig);
        if (codePrint != null) {
            codePrint.setFileConfig(fileConfig);
            codePrintRepository.save(codePrint);

            Map<String, Object> data = new HashMap<>();
            data.put("CodePrint", codePrint.getGeneratedCode());
            data.put("FileConfigResponse", response);

            document.getFileConfigs().add(fileConfig);
            documentRepository.save(document);
            return new ApiResponse<>(200, "Successfully created file config", data);
        }else {
            Map<String, Object> data = new HashMap<>();
            data.put("FileConfigResponse", response);

            document.getFileConfigs().add(fileConfig);
            documentRepository.save(document);

            return new ApiResponse<>(200, "Successfully created file config", data);
        }

    }

    @Override
    public FileConfigResponse getFileConfigById(Long id) {
        FileConfig fileConfig = fileConfigRepository.findById(id).orElseThrow(() -> new RuntimeException("Document not found"));
        return FileConfigResponse.toFileConfigResponse(fileConfig);
    }

    @Override
    public List<FileConfigResponse> getAllFileConfigsOfCurrentUser() {
        User student = authUtil.loggedInUser();
        List<FileConfig> fileConfigs = fileConfigRepository.getFileConfigsByStudent(student);
        return fileConfigs.stream().map(FileConfigResponse::toFileConfigResponse).toList();
    }

    @Override
    public ApiResponse<?> updateFileConfig(Long id, FileConfigRequest request) {
        FileConfig fileConfig = fileConfigRepository.findById(id).orElseThrow(() -> new RuntimeException("FileConfig not found"));
        fileConfig.setPaperSize(PaperSize.valueOf(request.getPaperSize()));
        fileConfig.setPaperRange(request.getPaperRange());
        fileConfig.setSides(Sides.valueOf(request.getSides()));
        fileConfig.setNumberOfCopies(request.getNumberOfCopies());
        fileConfig.setColor(request.isColor());
        fileConfig.setLayout(Layout.valueOf(request.getLayout()));
        fileConfig.setQRCode(request.isQrCode());
        fileConfig.setPageOfSheet(request.getPageOfSheet());
        fileConfig.setMargin(request.getMargin());
        fileConfig.setScale(request.getScale());
        fileConfigRepository.save(fileConfig);
        // check generateCode;
        CodePrint codePrint =  request.isQrCode()? CodePrint.builder()
                .generatedCode(generateCode())
                .codeStartDate(LocalDateTime.now())
                .codeEndDate(LocalDateTime.now().plusDays(1)).build() : null;

        if (codePrint != null) {
            codePrint.setFileConfig(fileConfig);
            codePrintRepository.save(codePrint);
            Map<String, String> data = new HashMap<>();
            data.put("CodePrint", codePrint.getGeneratedCode());
            return new ApiResponse<>(200, "Successfully created file config", data);
        }
        return new ApiResponse<>(200, "Successfully created file config", null);
    }

    @Override
    public MessageResponse deleteFileConfig(Long id) {
        FileConfig fileConfig = fileConfigRepository.findById(id).orElseThrow(() -> new RuntimeException("Document not found"));
        fileConfigRepository.deleteById(id);
        return new MessageResponse("FileConfiguration deleted successfully");
    }

    @Override
    public ApiResponse<?> getFileConfigByCode(String code) {
        CodePrint codePrint = codePrintRepository.findByGeneratedCode(code).orElseThrow(() -> new RuntimeException("Code print not found"));
        List<Printer> printers = printerRepository.findAll().stream().filter(printer -> printer.getStatusPrinter().equals(Status.ENABLE)).toList();
        FileConfig fileConfig = codePrint.getFileConfig();
        Map<String, Object> data = new HashMap<>();
        data.put("fileConfig", FileConfigResponse.toFileConfigResponse(fileConfig));
        data.put("printers", printers.stream().map(PrinterResponse::toPrinterResponse));
        return new ApiResponse<>(200, "Successful", data);
    }

    public String generateCode() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
