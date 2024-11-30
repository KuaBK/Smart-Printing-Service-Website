package hcmut.spss.be.dtos.response;

import hcmut.spss.be.entity.document.Document;
import hcmut.spss.be.entity.fileConfig.FileConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileConfigResponse {
    private Long id;
    private Document document;
    private String paperSize;
    private String paperRange;
    private String sides;
    private int numberOfCopies;
    private String layout;
    private boolean color;
    private boolean QRCode;
    private int pageOfSheet;
    private String margin;
    private int scale;

    public static FileConfigResponse toFileConfigResponse(FileConfig fileConfig) {
        return FileConfigResponse.builder()
                .id(fileConfig.getId())
                .document(fileConfig.getDocument())
                .paperSize(fileConfig.getPaperSize().name())
                .paperRange(fileConfig.getPaperRange())
                .sides(fileConfig.getSides().name())
                .numberOfCopies(fileConfig.getNumberOfCopies())
                .layout(fileConfig.getLayout().name())
                .color(fileConfig.getColor())
                .QRCode(fileConfig.getQRCode())
                .pageOfSheet(fileConfig.getPageOfSheet())
                .margin(fileConfig.getMargin())
                .scale(fileConfig.getScale())
                .build();
    }

}
