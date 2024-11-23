package hcmut.spss.be.service.impl;

import hcmut.spss.be.dtos.request.library.SearchDocumentRequest;
import hcmut.spss.be.dtos.request.library.ShareDocumentRequest;
import hcmut.spss.be.dtos.request.library.UnshareDocumentRequest;
import hcmut.spss.be.dtos.response.MessageResponse;
import hcmut.spss.be.dtos.response.library.*;
import hcmut.spss.be.entity.document.Document;
import hcmut.spss.be.entity.sharedLibrary.SharedLibrary;
import hcmut.spss.be.repository.DocumentRepository;
import hcmut.spss.be.repository.SharedLibraryRepository;
import hcmut.spss.be.service.SharedLibraryService;
import io.jsonwebtoken.io.IOException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SharedLibraryServiceImpl implements SharedLibraryService {
    private final SharedLibraryRepository sharedLibraryRepository;
    private final DocumentRepository documentRepository;

    public SharedLibraryServiceImpl(SharedLibraryRepository sharedLibraryRepository, DocumentRepository documentRepository) {
        this.sharedLibraryRepository = sharedLibraryRepository;
        this.documentRepository = documentRepository;
    }


//    public ShareDocumentResponse shareDocument(ShareDocumentRequest request) {
//        SharedLibrary defaultLibrary = sharedLibraryRepository.findByDefaultLibraryTrue()
//                .orElseThrow(() -> new RuntimeException("Default library not found"));
//
//        Document document = documentRepository.findById(request.getDocumentId())
//                .orElseThrow(() -> new RuntimeException("Document not found"));
//
//        document.shareWithLibrary(defaultLibrary);
//        documentRepository.save(document);
//
//        return new ShareDocumentResponse(
//                document.getDocumentId(),
//                document.getDocumentName(),
//                true,
//                defaultLibrary.getLibraryId(),
//                defaultLibrary.getLibraryName()
//        );
//    }
//
//    @Override
//    public ShareDocumentResponse unshareDocument(UnshareDocumentRequest request) {
//        Document document = documentRepository.findById(request.getDocumentId())
//                .orElseThrow(() -> new RuntimeException("Document not found"));
//
//        document.unshareFromLibrary();
//        documentRepository.save(document);
//
//        return new ShareDocumentResponse(document.getDocumentId(), document.getDocumentName(), false, null, null);
//    }

    public MessageResponse shareDocument(ShareDocumentRequest request) {
        SharedLibrary defaultLibrary = sharedLibraryRepository.findByDefaultLibraryTrue()
                .orElseThrow(() -> new RuntimeException("Default library not found"));

        Document document = documentRepository.findById(request.getDocumentId())
                .orElseThrow(() -> new RuntimeException("Document not found"));

        document.shareWithLibrary(defaultLibrary);
        documentRepository.save(document);

        return new MessageResponse("Chia sẻ tài liệu thành công với thư viện: " + defaultLibrary.getLibraryName());
    }

    public MessageResponse unshareDocument(UnshareDocumentRequest request) {
        Document document = documentRepository.findById(request.getDocumentId())
                .orElseThrow(() -> new RuntimeException("Document not found"));

        document.unshareFromLibrary();
        documentRepository.save(document);

        return new MessageResponse("Hủy chia sẻ tài liệu thành công");
    }


    @Override
    public List<SearchDocumentResponse> searchDocuments(SearchDocumentRequest request) {
        Long libraryId = 1L;
        String query = request.getQuery().trim().toLowerCase();

        List<Document> documents = documentRepository.searchByLibraryAndQuery(libraryId, query);

        return documents.stream()
                .map(doc -> new SearchDocumentResponse(doc.getDocumentId(), doc.getDocumentName(), doc.isShared()))
                .collect(Collectors.toList());
    }


    public List<SearchDocumentResponse> getAllDocuments() {
            SharedLibrary defaultLibrary = sharedLibraryRepository.findByDefaultLibraryTrue()
                    .orElseThrow(() -> new RuntimeException("Default library not found"));

            List<Document> documents = documentRepository.findAllBySharedLibrary(defaultLibrary);

            return documents.stream()
                    .map(doc -> new SearchDocumentResponse(doc.getDocumentId(), doc.getDocumentName(), true))
                    .toList();
    }
}
