package hcmut.spss.be.service.impl;

import hcmut.spss.be.dtos.request.library.CreateSharedLibraryRequest;
import hcmut.spss.be.dtos.request.library.SearchDocumentRequest;
import hcmut.spss.be.dtos.request.library.ShareDocumentRequest;
import hcmut.spss.be.dtos.request.library.UnshareDocumentRequest;
import hcmut.spss.be.dtos.response.MessageResponse;
import hcmut.spss.be.dtos.response.library.CreateSharedLibraryResponse;
import hcmut.spss.be.dtos.response.library.SearchDocumentResponse;
import hcmut.spss.be.dtos.response.library.ShareDocumentResponse;
import hcmut.spss.be.dtos.response.library.UnshareDocumentResponse;
import hcmut.spss.be.entity.document.Document;
import hcmut.spss.be.entity.sharedLibrary.SharedLibrary;
import hcmut.spss.be.entity.user.User;
import hcmut.spss.be.repository.DocumentRepository;
import hcmut.spss.be.repository.SharedLibraryRepository;
import hcmut.spss.be.repository.UserRepository;
import hcmut.spss.be.service.SharedLibraryService;
import hcmut.spss.be.utils.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SharedLibraryServiceImpl implements SharedLibraryService {

    private final SharedLibraryRepository sharedLibraryRepository;
    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final AuthUtil authUtil;

    @Autowired
    public SharedLibraryServiceImpl(SharedLibraryRepository sharedLibraryRepository, UserRepository userRepository,
                                    AuthUtil authUtil, DocumentRepository documentRepository) {
        this.sharedLibraryRepository = sharedLibraryRepository;
        this.userRepository = userRepository;
        this.authUtil = authUtil;
        this.documentRepository = documentRepository;
    }

    @Override
    public CreateSharedLibraryResponse createSharedLibrary(CreateSharedLibraryRequest request) {
        Long currentUserId = authUtil.loggedInUserId();
        Optional<User> user = userRepository.findById(currentUserId);

        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        SharedLibrary sharedLibrary = SharedLibrary.builder()
                .libraryName(request.getLibraryName())
                .sharedDate(LocalDateTime.now())
                .student(user.get())
                .build();

        sharedLibraryRepository.save(sharedLibrary);

        return new CreateSharedLibraryResponse(
                sharedLibrary.getLibraryId(),
                sharedLibrary.getLibraryName(),
                sharedLibrary.getSharedDate().toString()
        );
    }

    @Override
    public ShareDocumentResponse shareDocument(ShareDocumentRequest request) {
        Document document = documentRepository.findById(request.getDocumentId())
                .orElseThrow(() -> new RuntimeException("Document not found"));
        SharedLibrary sharedLibrary = sharedLibraryRepository.findById(request.getLibraryId())
                .orElseThrow(() -> new RuntimeException("Library not found"));

        // Chia sẻ tài liệu vào thư viện
        document.shareWithLibrary(sharedLibrary, request.getCategory());
        documentRepository.save(document);

        return new ShareDocumentResponse(
                document.getDocumentId(),
                document.getDocumentName(),
                document.getCategory(),
                sharedLibrary.getLibraryId(),
                sharedLibrary.getLibraryName(),
                true
        );
    }

    @Override
    public UnshareDocumentResponse unshareDocument(UnshareDocumentRequest request) {
        Document document = documentRepository.findById(request.getDocumentId())
                .orElseThrow(() -> new RuntimeException("Document not found"));

        SharedLibrary sharedLibrary = document.getSharedLibrary();
        if (sharedLibrary == null) {
            throw new RuntimeException("Document is not shared");
        }

        document.setSharedLibrary(null);
        documentRepository.save(document);

        return new UnshareDocumentResponse(
                document.getDocumentId(),
                document.getDocumentName(),
                false,
                sharedLibrary.getLibraryId(),
                sharedLibrary.getLibraryName()
        );
    }

    @Override
    public List<SearchDocumentResponse> searchDocument(SearchDocumentRequest request) {
        List<Document> documents = documentRepository.findByDocumentNameContainingIgnoreCase(request.getQuery());
        return documents.stream().map(document -> new SearchDocumentResponse(
                document.getDocumentId(),
                document.getDocumentName(),
                document.getCategory(),
                document.getSharedLibrary() != null
        )).collect(Collectors.toList());
    }
}
