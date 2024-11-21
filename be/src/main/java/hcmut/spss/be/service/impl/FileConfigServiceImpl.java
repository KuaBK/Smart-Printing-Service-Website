package hcmut.spss.be.service.impl;

import hcmut.spss.be.dtos.request.FileConfigRequest;
import hcmut.spss.be.dtos.response.ApiResponse;
import hcmut.spss.be.dtos.response.MessageResponse;
import hcmut.spss.be.dtos.response.FileConfigResponse;
import hcmut.spss.be.entity.codePrint.CodePrint;
import hcmut.spss.be.entity.document.Document;
import hcmut.spss.be.entity.fileConfig.*;
import hcmut.spss.be.repository.CodePrintRepository;
import hcmut.spss.be.repository.DocumentRepository;
import hcmut.spss.be.repository.FileConfigRepository;
import hcmut.spss.be.service.FileConfigService;
import hcmut.spss.be.utils.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public FileConfigServiceImpl(FileConfigRepository fileConfigRepository) {
        this.fileConfigRepository = fileConfigRepository;
    }

    @Override
    public ApiResponse<?> createFileConfig(FileConfigRequest request, Long id) {
        // find document to config
        Document document = documentRepository.findById(id).orElseThrow(() -> new RuntimeException("Document not found"));


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
                .build();

        if (codePrint != null) {
            codePrintRepository.save(codePrint);
            fileConfig.setCodePrint(codePrint);
            Map<String, String> data = new HashMap<>();
            data.put("CodePrint", codePrint.getGeneratedCode());
            fileConfigRepository.save(fileConfig);
            document.getFileConfigs().add(fileConfig);
            documentRepository.save(document);
            return new ApiResponse<>(200, "Successfully created file config", data);
        }else {
            fileConfigRepository.save(fileConfig);
            document.getFileConfigs().add(fileConfig);
            documentRepository.save(document);

            return new ApiResponse<>(200, "Successfully created file config", null);
        }

    }

    @Override
    public FileConfigResponse getFileConfigById(Long id) {
        FileConfig fileConfig = fileConfigRepository.findById(id).orElseThrow(() -> new RuntimeException("Document not found"));
        return FileConfigResponse.toFileConfigResponse(fileConfig);
    }

    @Override
    public List<FileConfigResponse> getAllFileConfigsOfCurrentUser() {
        Long currentUserId = authUtil.loggedInUserId();
        List<FileConfig> fileConfigs = fileConfigRepository.findAllByStudentId(currentUserId);
        return fileConfigs.stream().map(FileConfigResponse::toFileConfigResponse).toList();
    }

    @Override
    public MessageResponse updateFileConfig(Long id, FileConfigRequest request) {
        FileConfig fileConfig = fileConfigRepository.findById(id).orElseThrow(() -> new RuntimeException("Document not found"));
        fileConfig.setPaperSize(PaperSize.valueOf(request.getPaperSize()));
        fileConfig.setPaperRange(request.getPaperRange());
        fileConfig.setSides(Sides.valueOf(request.getSides()));
        fileConfig.setNumberOfCopies(request.getNumberOfCopies());
        fileConfig.setColor(request.isColor());
        fileConfig.setLayout(Layout.valueOf(request.getLayout()));
        fileConfigRepository.save(fileConfig);
        return new MessageResponse("FileConfiguration updated successfully");
    }

    @Override
    public MessageResponse deleteFileConfig(Long id) {
        FileConfig fileConfig = fileConfigRepository.findById(id).orElseThrow(() -> new RuntimeException("Document not found"));
        fileConfigRepository.deleteById(id);
        return new MessageResponse("FileConfiguration deleted successfully");
    }

    public String generateCode() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
