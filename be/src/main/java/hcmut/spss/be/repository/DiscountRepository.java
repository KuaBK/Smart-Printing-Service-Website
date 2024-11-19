package hcmut.spss.be.repository;

import hcmut.spss.be.entity.discount.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Optional<Discount> findByDiscountCode(String discountCode);
}
