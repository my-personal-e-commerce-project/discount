package microservice.cloud.discount.discount.application.ports.out;

import java.util.List;

import microservice.cloud.discount.discount.application.dtos.DiscountReadDTO;
import microservice.cloud.discount.shared.application.dto.Pagination;

public interface DiscountReadRepository {

    public Pagination<DiscountReadDTO> listDiscounts(
        int page,
        int size,
        List<String> allowedCategories,
        Boolean globalCategories,
        Boolean isActive,
        Integer minStock,
        Integer maxStock,
        Double minPrice,
        Double maxPrice
    );
}
