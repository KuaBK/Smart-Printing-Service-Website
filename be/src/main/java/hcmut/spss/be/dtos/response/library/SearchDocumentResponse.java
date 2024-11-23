package hcmut.spss.be.dtos.response.library;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchDocumentResponse {
    private Long documentId;
    private String documentName;
    private String url;
    private Boolean isShared;
    private String headline;
    private String facultyName;
    private String subject;
    private String category;
    private String semester;
    private String uploadTime;
}
