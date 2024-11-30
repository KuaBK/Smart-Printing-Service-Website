package hcmut.spss.be.repository;

import hcmut.spss.be.entity.payment.Payment;
import hcmut.spss.be.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByStudent(User student);

    @Query(value = "SELECT SUM(num_pages_bought) FROM payment " +
            "WHERE payment_date >= :startOfDay AND payment_date < :endOfDay", nativeQuery = true)
    Integer countPapersInDay(@Param("startOfDay") String startOfDay, @Param("endOfDay") String endOfDay);

    @Query(value = "SELECT EXTRACT(MONTH FROM payment_date) AS month, " +
            "EXTRACT(YEAR FROM payment_date) AS year, " +
            "SUM(amount_paid) AS totalAmount " +
            "FROM payment p " +
            "WHERE p.payment_date >= CURRENT_DATE - INTERVAL 6 MONTH " +
            "GROUP BY EXTRACT(YEAR FROM payment_date), EXTRACT(MONTH FROM payment_date) " +
            "ORDER BY year DESC, month DESC", nativeQuery = true)
    List<Object[]> countTotalAmountLast6Months();

    List<Payment> findAll();
}
