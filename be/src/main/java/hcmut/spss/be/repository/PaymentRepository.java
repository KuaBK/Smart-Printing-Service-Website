package hcmut.spss.be.repository;

import hcmut.spss.be.entity.payment.Payment;
import hcmut.spss.be.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByStudent(User student);
}
