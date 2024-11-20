package hcmut.spss.be.service;

import hcmut.spss.be.dtos.request.library.CreateSharedLibraryRequest;
import hcmut.spss.be.dtos.request.library.SearchDocumentRequest;
import hcmut.spss.be.dtos.request.library.ShareDocumentRequest;
import hcmut.spss.be.dtos.request.library.UnshareDocumentRequest;
import hcmut.spss.be.dtos.response.ApiResponse;
import hcmut.spss.be.dtos.response.MessageResponse;
import hcmut.spss.be.dtos.response.library.CreateSharedLibraryResponse;
import hcmut.spss.be.dtos.response.library.SearchDocumentResponse;
import hcmut.spss.be.dtos.response.library.ShareDocumentResponse;
import hcmut.spss.be.dtos.response.library.UnshareDocumentResponse;

import java.util.List;

public interface SharedLibraryService {
    CreateSharedLibraryResponse createSharedLibrary(CreateSharedLibraryRequest request);
    ShareDocumentResponse shareDocument(ShareDocumentRequest request);
    UnshareDocumentResponse unshareDocument(UnshareDocumentRequest request);
    List<SearchDocumentResponse> searchDocument(SearchDocumentRequest request);
}
