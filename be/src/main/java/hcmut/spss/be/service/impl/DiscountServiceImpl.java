package hcmut.spss.be.service.impl;

import hcmut.spss.be.dtos.request.DiscountRequest;
import hcmut.spss.be.dtos.response.DiscountResponse;
import hcmut.spss.be.dtos.response.MessageResponse;
import hcmut.spss.be.entity.discount.Discount;
import hcmut.spss.be.repository.DiscountRepository;
import hcmut.spss.be.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Override
    public MessageResponse makeDiscount(DiscountRequest request) {
        Discount discount = Discount.builder()
                .semester(request.getSemester())
                .discountCode(request.isAll()? "All" : generateDiscountCode())
                .pagesFree(request.getPagesFree())
                .startDate(request.getStartDate())
                .expirationDate(request.getExpirationDate())
                .build();
        discountRepository.save(discount);
        return new MessageResponse("created discount");
    }

    @Override
    public List<DiscountResponse> getAllDiscounts() {
        return discountRepository.findAll().stream().map(DiscountResponse::toDiscountResponse).toList();
    }

    @Override
    public DiscountResponse getDiscountByCode(String code) {
        Discount discount = discountRepository.findByDiscountCode(code).orElseThrow(() -> new IllegalArgumentException("discount code not found"));
        return DiscountResponse.toDiscountResponse(discount);
    }

    public String generateDiscountCode() {
        return "DIS-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();
    }
}
