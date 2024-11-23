package hcmut.spss.be.entity.document;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import hcmut.spss.be.entity.fileConfig.FileConfig;
import hcmut.spss.be.entity.sharedLibrary.SharedLibrary;
import hcmut.spss.be.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")
    private Long documentId;

    @Column(name = "document_name")
    private String documentName;

    @Column(name = "size")
    private Long size;

    @Column(name = "type")
    private String type;

    @Column(name = "num_of_page")
    private int numOfPage;

    @Column(name = "shared")
    private boolean shared;

    @Column(name = "url")
    private String url;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "upload_time", updatable = false)
    private LocalDateTime uploadTime;

    @Column(name = "headline")
    private String headline;

    @Column(name = "faculty_name")
    private String facultyName;

    @Column(name = "subject")
    private String subject;

    @Column(name = "semester")
    private String semester;

    @Column(name = "category")
    private String category;


    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "user_id")
    @JsonManagedReference
    private User student;

    @OneToMany(mappedBy = "document")
    @JsonBackReference
    private Set<FileConfig> fileConfigs = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "library_id")
    @JsonBackReference
    private SharedLibrary sharedLibrary;

    public void shareWithLibrary(SharedLibrary library) {
        this.sharedLibrary = library;
    }

    public void unshareFromLibrary() {
        this.sharedLibrary = null;
    }
}
