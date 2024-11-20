package hcmut.spss.be.dtos.response.library;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateSharedLibraryResponse {
    private Long libraryId;
    private String libraryName;
    private String sharedDate;
}
