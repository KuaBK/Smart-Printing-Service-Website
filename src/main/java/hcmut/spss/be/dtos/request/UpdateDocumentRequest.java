package hcmut.spss.be.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateDocumentRequest {
    private String headline;
    private String semester;
    private String category;
    private String subject;
    private String facultyName;
}
