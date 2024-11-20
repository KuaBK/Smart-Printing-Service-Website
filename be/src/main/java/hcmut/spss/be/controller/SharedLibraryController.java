package hcmut.spss.be.controller;

import hcmut.spss.be.dtos.request.library.CreateSharedLibraryRequest;
import hcmut.spss.be.dtos.request.library.SearchDocumentRequest;
import hcmut.spss.be.dtos.request.library.ShareDocumentRequest;
import hcmut.spss.be.dtos.request.library.UnshareDocumentRequest;
import hcmut.spss.be.dtos.response.MessageResponse;
import hcmut.spss.be.dtos.response.library.CreateSharedLibraryResponse;
import hcmut.spss.be.dtos.response.library.SearchDocumentResponse;
import hcmut.spss.be.dtos.response.library.ShareDocumentResponse;
import hcmut.spss.be.dtos.response.library.UnshareDocumentResponse;
import hcmut.spss.be.service.SharedLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sharedLibrary")
public class SharedLibraryController {

    private final SharedLibraryService sharedLibraryService;

    @Autowired
    public SharedLibraryController(SharedLibraryService sharedLibraryService) {
        this.sharedLibraryService = sharedLibraryService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSharedLibrary(@RequestBody CreateSharedLibraryRequest request) {
        try {
            CreateSharedLibraryResponse response = sharedLibraryService.createSharedLibrary(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping("/share")
    public ResponseEntity<?> shareDocument(@RequestBody ShareDocumentRequest request) {
        try {
            ShareDocumentResponse response = sharedLibraryService.shareDocument(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/unshare")
    public ResponseEntity<?> unshareDocument(@RequestBody UnshareDocumentRequest request) {
        try {
            UnshareDocumentResponse response = sharedLibraryService.unshareDocument(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/search")
    public ResponseEntity<List<SearchDocumentResponse>> searchDocument(@RequestBody SearchDocumentRequest request) {
        try {
            List<SearchDocumentResponse> response = sharedLibraryService.searchDocument(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}


