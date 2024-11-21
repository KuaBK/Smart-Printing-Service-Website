package hcmut.spss.be.service;

import hcmut.spss.be.dtos.response.DocumentResponse;
import hcmut.spss.be.entity.document.Document;

import java.util.List;

public interface DocumentService {
    List<DocumentResponse> getAllDocuments();
    DocumentResponse getDocument(Long documentId);
}
