package hcmut.spss.be.service;

import hcmut.spss.be.dtos.request.DiscountRequest;
import hcmut.spss.be.dtos.response.DiscountResponse;
import hcmut.spss.be.dtos.response.MessageResponse;
import hcmut.spss.be.entity.discount.Discount;

import java.util.List;

public interface DiscountService {
    MessageResponse makeDiscount(DiscountRequest request);

    List<DiscountResponse> getAllDiscounts();

    DiscountResponse getDiscountByCode(String code);
}
