package hcmut.spss.be.repository;

import hcmut.spss.be.entity.document.Document;
import hcmut.spss.be.entity.sharedLibrary.SharedLibrary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
     List<Document> findByDocumentNameContainingIgnoreCase(String query);
}
