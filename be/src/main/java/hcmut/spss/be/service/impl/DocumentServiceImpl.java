package hcmut.spss.be.service.impl;

import hcmut.spss.be.dtos.response.document.DocumentResponse;
import hcmut.spss.be.dtos.request.UpdateDocumentRequest;
import hcmut.spss.be.entity.document.Document;
import hcmut.spss.be.repository.DocumentRepository;
import hcmut.spss.be.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {
    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public List<DocumentResponse> getAllDocuments() {
        return documentRepository.findAll().stream().map(DocumentResponse::toDocumentResponse).toList();
    }

    @Override
    public DocumentResponse getDocument(Long documentId) {
        Document document =  documentRepository.findById(documentId).orElseThrow(() -> new RuntimeException("Document not found"));
        return DocumentResponse.toDocumentResponse(document);
    }

    @Override
    public DocumentResponse updateDocument(Long documentId, UpdateDocumentRequest request) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        if (request.getHeadline() != null) {
            document.setHeadline(request.getHeadline());
        }
        if (request.getSemester() != null) {
            document.setSemester(request.getSemester());
        }
        if (request.getCategory() != null) {
            document.setCategory(request.getCategory());
        }
        if (request.getSubject() != null) {
            document.setSubject(request.getSubject());
        }
        if (request.getFacultyName() != null) {
            document.setFacultyName(request.getFacultyName());
        }

        documentRepository.save(document);
        return DocumentResponse.toDocumentResponse(document);
    }
}
