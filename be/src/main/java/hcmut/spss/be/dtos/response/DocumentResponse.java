package hcmut.spss.be.dtos.response;

import hcmut.spss.be.entity.document.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentResponse {
    private Long id;
    private String documentName;
    private Long size;
    private int numberOfPages;
    private boolean shared;
    private String url;
    private Date uploadTime;
    private String headline;
    private String facultyName;
    private String subject;
    private String semester;
    private String category;

    public static DocumentResponse toDocumentResponse(Document document) {
        return DocumentResponse.builder()
                .id(document.getDocumentId())
                .documentName(document.getDocumentName())
                .size(document.getSize())
                .numberOfPages(document.getNumOfPage())
                .shared(document.isShared())
                .url(document.getUrl())
                .uploadTime(document.getUploadTime())
                .headline(document.getHeadline())
                .facultyName(document.getFacultyName())
                .subject(document.getSubject())
                .semester(document.getSemester())
                .category(document.getCategory())
                .build();
    }
}
