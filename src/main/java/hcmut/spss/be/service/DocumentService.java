package hcmut.spss.be.service;

import hcmut.spss.be.dtos.response.document.DocumentResponse;
import hcmut.spss.be.dtos.request.UpdateDocumentRequest;

import java.util.List;

public interface DocumentService {
    List<DocumentResponse> getAllDocuments();
    List<DocumentResponse> getDocsByCurrentUser();
    DocumentResponse getDocument(Long documentId);
    DocumentResponse updateDocument(Long documentId, UpdateDocumentRequest request);
}
