package microservice.cloud.discount.discount.application.ports.out;

import microservice.cloud.discount.discount.application.dtos.DiscountReadDTO;
import microservice.cloud.discount.shared.application.dto.Pagination;

public interface DiscountReadRepository {

    public Pagination<DiscountReadDTO> listDiscounts(int page, int size);
}
