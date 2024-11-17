package hcmut.spss.be.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import hcmut.spss.be.entity.discount.Discount;
import hcmut.spss.be.entity.document.Document;
import hcmut.spss.be.entity.notification.Notification;
import hcmut.spss.be.entity.payment.Payment;
import hcmut.spss.be.entity.printJob.PrintJob;
import hcmut.spss.be.entity.report.Report;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotBlank
    @Size(min = 3, max = 50)
    @Column(name = "username", unique = true)
    private String username;

    @NotBlank
    @Size(min = 3, max = 100)
    @Column(name = "name")
    private String name;

    @NotBlank
    @Size(max = 50)
    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "mssv", unique = true)
    private String mssv;

    @Size(min = 3)
    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Size(max = 10)
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "avatar_url")
    private String avatarUrl;

    private boolean enabled = true;
    private boolean online = false;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Document> documents;

    @OneToMany(mappedBy = "student")
    @JsonManagedReference
    private List<Payment> paymentList;


    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PrintJob> printJobList;

    @OneToMany(mappedBy = "spso")
    @JsonManagedReference
    private List<Discount> discountList;

    @OneToMany(mappedBy = "spso")
    @JsonManagedReference
    private List<Notification> notificationList;

    @OneToMany(mappedBy = "spso")
    @JsonManagedReference
    private List<Report> reportList;
}
