package hcmut.spss.be.dtos.response;

import hcmut.spss.be.entity.discount.Discount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscountResponse {
    private Long id;
    private String semester;
    private int pagesFree;
    private LocalDate startDate;
    private LocalDate expirationDate;
    private String discountCode;

    public static DiscountResponse toDiscountResponse(Discount discount) {
        return DiscountResponse.builder()
                .id(discount.getDiscountId())
                .semester(discount.getSemester())
                .pagesFree(discount.getPagesFree())
                .startDate(discount.getStartDate())
                .expirationDate(discount.getExpirationDate())
                .discountCode(discount.getDiscountCode())
                .build();
    }
}
