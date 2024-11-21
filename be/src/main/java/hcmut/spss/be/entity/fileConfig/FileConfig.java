package hcmut.spss.be.entity.fileConfig;

import com.fasterxml.jackson.annotation.JsonBackReference;
import hcmut.spss.be.entity.document.Document;
import hcmut.spss.be.entity.printer.Printer;
import hcmut.spss.be.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "fileConfig")
public class FileConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "paper_size")
    PaperSize paperSize;

    @Column(name = "scale")
    int scale;

    @Enumerated(EnumType.STRING)
    @Column(name = "pages_print")
    PagesPrint pagesPrint;

    @Column(name = "paper_range")
    String paperRange;

    @Enumerated(EnumType.STRING)
    @Column(name = "sides")
    Sides sides;

    @Column(name = "number_of_copies")
    int numberOfCopies;

    @Column(name = "page_of_sheet")
    int pageOfSheet;

    @Enumerated(EnumType.STRING)
    @Column(name = "layout")
    Layout layout;

    @Column(name = "margin")
    String margin;

    @Column(name = "color")
    Boolean color;

    @Column(name = "qr_code")
    Boolean QRCode;

    @OneToOne
    @JoinColumn(name = "document_id")
    @JsonBackReference
    Document document;
}