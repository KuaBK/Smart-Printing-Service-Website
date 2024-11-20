package hcmut.spss.be.dtos.request.library;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ShareDocumentRequest {
    private Long documentId;
    private Long libraryId;
    private String category;
}
