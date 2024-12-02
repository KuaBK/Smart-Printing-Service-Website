package hcmut.spss.be.dtos.response.document;

import hcmut.spss.be.entity.document.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentResponse {
    private Long id;
    private String documentName;
    private Long size;
    private int numOfPage;
    private boolean shared;
    private String url;
    private String uploadTime;
    private String headline;
    private String facultyName;
    private String subject;
    private String semester;
    private String category;

    public static DocumentResponse toDocumentResponse(Document document) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm - dd/MM/yyyy");
        return DocumentResponse.builder()
                .id(document.getDocumentId())
                .documentName(document.getDocumentName())
                .size(document.getSize())
                .numOfPage(document.getNumOfPage())
                .shared(document.isShared())
                .url(document.getUrl())
                .uploadTime(document.getUploadTime().format(formatter))
                .headline(document.getHeadline())
                .facultyName(document.getFacultyName())
                .subject(document.getSubject())
                .semester(document.getSemester())
                .category(document.getCategory())
                .build();
    }
}
