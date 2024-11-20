package hcmut.spss.be.dtos.response.library;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchDocumentResponse {
    private Long documentId;     // ID của tài liệu
    private String documentName; // Tên của tài liệu
    private Boolean isShared;    // Trạng thái đã chia sẻ hay chưa
}
