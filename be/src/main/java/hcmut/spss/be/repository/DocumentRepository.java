package hcmut.spss.be.repository;

import hcmut.spss.be.entity.document.Document;
import hcmut.spss.be.entity.sharedLibrary.SharedLibrary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
//     @Query("SELECT d FROM Document d WHERE d.sharedLibrary.libraryId = :libraryId AND (d.documentName LIKE %:query%)")
//     List<Document> searchByLibraryAndQuery(@Param("libraryId") Long libraryId, @Param("query") String query);
     @Query("SELECT d FROM Document d WHERE d.sharedLibrary.id = :libraryId AND d.documentName LIKE %:query% " +
             "ORDER BY LENGTH(d.documentName) - LENGTH(REPLACE(d.documentName, :query, '')) DESC")
     List<Document> searchByLibraryAndQuery(@Param("libraryId") Long libraryId, @Param("query") String query);

     List<Document> findAllBySharedLibrary(SharedLibrary sharedLibrary);
}
