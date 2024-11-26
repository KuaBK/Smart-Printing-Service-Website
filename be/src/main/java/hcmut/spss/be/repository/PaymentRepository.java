package hcmut.spss.be.repository;

import hcmut.spss.be.entity.payment.Payment;
import hcmut.spss.be.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByStudent(User student);
    List<Payment> findAll();
}
