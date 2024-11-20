package hcmut.spss.be.dtos.response.library;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnshareDocumentResponse {
    private Long documentId;
    private String documentName;
    private Boolean isShared;
    private Long libraryId;
    private String libraryName;
}
