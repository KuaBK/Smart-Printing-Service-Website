package hcmut.spss.be.entity.sharedLibrary;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import hcmut.spss.be.entity.document.Document;
import hcmut.spss.be.entity.printJob.PrintJob;
import hcmut.spss.be.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "library")
public class SharedLibrary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "library_id")
    Long libraryId;

    @Column(name = "library_name", unique = true, nullable = false)
    String libraryName;

    @Column(name = "default_library", nullable = false)
    private Boolean defaultLibrary;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    LocalDateTime createdDate;

    @OneToMany(mappedBy = "sharedLibrary", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    List<Document> documents;
}
