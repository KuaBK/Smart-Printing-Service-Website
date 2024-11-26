package hcmut.spss.be.entity.discount;

import com.fasterxml.jackson.annotation.JsonBackReference;
import hcmut.spss.be.entity.payment.Payment;
import hcmut.spss.be.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "discount")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discount_id")
    Long discountId;

    @Column(name = "semester")
    String semester;

    @Column(name = "discount_code", unique = true)
    String discountCode;

    @Column(name = "pages_free")
    int pagesFree;

    LocalDate startDate;
    LocalDate expirationDate;

    @OneToOne(mappedBy = "discount")
    @JsonBackReference
    Payment payment;



    @CreationTimestamp
    @Column(updatable = false)
    LocalDateTime creationDate;

    @UpdateTimestamp
    @Column(updatable = false)
    LocalDateTime updateDate;

    @ManyToMany(mappedBy = "discounts")
    @JsonBackReference
    Set<User> users = new HashSet<>();
}
