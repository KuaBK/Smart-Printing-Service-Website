package hcmut.spss.be.service;

import hcmut.spss.be.dtos.response.DocumentResponse;
import hcmut.spss.be.entity.document.Document;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {
    DocumentResponse uploadFile(MultipartFile file) throws IOException;
}
