package hcmut.spss.be.entity.fileConfig;

import com.fasterxml.jackson.annotation.JsonBackReference;
import hcmut.spss.be.entity.document.Document;
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
    @Column(name = "fileID")
    Long fileID;

    @Column(name = "papersize")
    PaperSize papersize;

    @Column(name = "paperRange")
    String paperRange;

    @Column(name = "sides")
    Sides sides;

    @Column(name = "numberOfCopies")
    int numberOfCopies;

    @Column(name = "layout")
    Layout layout;

    @Column(name = "color")
    Boolean color;

    @OneToOne
    @JoinColumn(name = "document_id")
    @JsonBackReference
    Document document;
}
