package hcmut.spss.be.service;

import hcmut.spss.be.dtos.request.FileConfigRequest;
import hcmut.spss.be.dtos.response.ApiResponse;
import hcmut.spss.be.dtos.response.FileConfigResponse;
import hcmut.spss.be.dtos.response.MessageResponse;

import java.util.List;

public interface FileConfigService {
    ApiResponse<?> createFileConfig(FileConfigRequest request, Long id);
    FileConfigResponse getFileConfigById(Long id);
    List<FileConfigResponse> getAllFileConfigsOfCurrentUser();
    MessageResponse updateFileConfig(Long id, FileConfigRequest request);
    MessageResponse deleteFileConfig(Long id);
    ApiResponse<?> getFileConfigByCode(String code);
}
