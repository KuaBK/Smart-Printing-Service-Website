package hcmut.spss.be.entity.fileConfig;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import hcmut.spss.be.entity.codePrint.CodePrint;
import hcmut.spss.be.entity.document.Document;
import hcmut.spss.be.entity.printer.Printer;
import hcmut.spss.be.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToOne
    @JoinColumn(name = "document_id")
    @JsonManagedReference
    Document document;

    @OneToOne(mappedBy = "fileConfig")
    CodePrint codePrint;

    @ManyToOne
    @JoinColumn(name = "student_id")
    User student;
}