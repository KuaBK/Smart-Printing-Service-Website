package hcmut.spss.be.repository;

import hcmut.spss.be.entity.discount.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Optional<Discount> findByDiscountCode(String discountCode);

    // Query tìm tất cả discount có hiệu lực trong ngày hôm nay và có discountCode là 'All'
    @Query("SELECT d FROM Discount d WHERE d.discountCode = 'All' AND d.startDate <= :today AND d.expirationDate >= :today")
    List<Discount> findActiveDiscountsForToday(@Param("today") LocalDate today);
}
