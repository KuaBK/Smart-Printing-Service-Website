package hcmut.spss.be.dtos.response.library;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShareDocumentResponse {

    private Long documentId;
    private String documentName;
    private String category;
    private Long libraryId;
    private String libraryName;
    private Boolean isShared;
}
