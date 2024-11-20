package hcmut.spss.be.dtos.request.library;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSharedLibraryRequest {
    private String libraryName;
}
