package hcmut.spss.be.controller;

import hcmut.spss.be.dtos.response.document.DocumentResponse;
import hcmut.spss.be.dtos.request.UpdateDocumentRequest;
import hcmut.spss.be.service.CloudinaryService;
import hcmut.spss.be.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private DocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestBody MultipartFile file) {
        try {
            // Upload file và lưu thông tin vào DB
            DocumentResponse uploadedFile = cloudinaryService.uploadFile(file);

            // Trả về thông tin file đã lưu trong DB
            return ResponseEntity.ok(uploadedFile);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SPSO')")
    public ResponseEntity<?> getAllFiles() {
        try {
            List<DocumentResponse> files = documentService.getAllDocuments();
            return ResponseEntity.ok(files);
        }catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving files: " + e.getMessage());
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserFiles() {
        try {
            return ResponseEntity.ok(documentService.getDocsByCurrentUser());
        }catch (Exception e){
            return ResponseEntity.status(500).body("Error retrieving files: " + e.getMessage());
        }
    }

    // API để lấy file theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getFileById(@PathVariable Long id) {
        try {
            DocumentResponse document = documentService.getDocument(id);
            return ResponseEntity.ok(document);
        }catch (Exception e){
            return ResponseEntity.status(500).body("Error retrieving file: " + e.getMessage());
        }
    }

    @PatchMapping("/{documentId}")
    public ResponseEntity<DocumentResponse> updateDocument(
            @PathVariable Long documentId,
            @RequestBody UpdateDocumentRequest request) {
        DocumentResponse updatedDocument = documentService.updateDocument(documentId, request);
        return ResponseEntity.ok(updatedDocument);
    }
}
