package hcmut.spss.be.controller;

import hcmut.spss.be.dtos.request.library.SearchDocumentRequest;
import hcmut.spss.be.dtos.request.library.ShareDocumentRequest;
import hcmut.spss.be.dtos.request.library.UnshareDocumentRequest;
import hcmut.spss.be.dtos.response.MessageResponse;
import hcmut.spss.be.dtos.response.library.*;
import hcmut.spss.be.service.SharedLibraryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/library")
public class SharedLibraryController {
    private final SharedLibraryService sharedLibraryService;

    public SharedLibraryController(SharedLibraryService sharedLibraryService) {
        this.sharedLibraryService = sharedLibraryService;
    }

//    @PostMapping("/share")
//    public ResponseEntity<?> shareDocument(@RequestBody ShareDocumentRequest request) {
//        return ResponseEntity.ok(sharedLibraryService.shareDocument(request));
//    }
//
//    @PostMapping("/unshare")
//    public ResponseEntity<?> unshareDocument(@RequestBody UnshareDocumentRequest request) {
//        return ResponseEntity.ok(sharedLibraryService.unshareDocument(request));
//    }
    @PostMapping("/share")
    public ResponseEntity<MessageResponse> shareDocument(@RequestBody ShareDocumentRequest request) {
        MessageResponse response = sharedLibraryService.shareDocument(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/unshare")
    public ResponseEntity<MessageResponse> unshareDocument(@RequestBody UnshareDocumentRequest request) {
        MessageResponse response = sharedLibraryService.unshareDocument(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchDocuments(@RequestBody SearchDocumentRequest request) {
        try {
            return ResponseEntity.ok(sharedLibraryService.searchDocuments(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<SearchDocumentResponse>> getAllDocuments() {
        return ResponseEntity.ok(sharedLibraryService.getAllDocuments());
    }
}


