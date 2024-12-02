package hcmut.spss.be.entity.codePrint;

import hcmut.spss.be.entity.fileConfig.FileConfig;
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
@Table(name = "codePrint")
public class CodePrint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_id")
    Long codeId;

    @Column(name = "generated_code", unique = true)
    String generatedCode;

    @Column(name = "code_start_date", updatable = false)
    LocalDateTime codeStartDate;

    @Column(name = "code_end_date")
    LocalDateTime codeEndDate;

    @Column(name = "is_used")
    boolean used = false;

    @OneToOne
    @JoinColumn(name = "file_config_id")
    FileConfig fileConfig;
}
