package hcmut.spss.be.service;

import hcmut.spss.be.dtos.request.library.SearchDocumentRequest;
import hcmut.spss.be.dtos.request.library.ShareDocumentRequest;
import hcmut.spss.be.dtos.request.library.UnshareDocumentRequest;
import hcmut.spss.be.dtos.response.MessageResponse;
import hcmut.spss.be.dtos.response.library.*;

import java.io.File;
import java.util.List;

public interface SharedLibraryService {
    MessageResponse shareDocument(ShareDocumentRequest request);

    MessageResponse unshareDocument(UnshareDocumentRequest request);

    List<SearchDocumentResponse> searchDocuments(SearchDocumentRequest request);

    List<SearchDocumentResponse> getAllDocuments();
}
