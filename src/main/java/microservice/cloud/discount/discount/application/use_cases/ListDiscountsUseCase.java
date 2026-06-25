package microservice.cloud.discount.discount.application.use_cases;

import java.util.List;

import microservice.cloud.discount.discount.application.dtos.DiscountReadDTO;
import microservice.cloud.discount.discount.application.ports.out.DiscountReadRepository;
import microservice.cloud.discount.shared.application.dto.Pagination;

public class ListDiscountsUseCase {
    private final DiscountReadRepository discountReadRepository;

    public ListDiscountsUseCase(
        DiscountReadRepository discountReadRepository
    ) {
        this.discountReadRepository = discountReadRepository;
    }

    public Pagination<DiscountReadDTO> execute(
        int page, 
        int size,
        List<String> allowedCategories,
        Boolean globalCategories,
        Boolean isActive,
        Integer minStock,
        Integer maxStock,
        Double minPrice,
        Double maxPrice
    ) {

        return discountReadRepository.listDiscounts(
            page,
            size,
            allowedCategories,
            globalCategories,
            isActive,
            minStock,
            maxStock,
            minPrice,
            maxPrice
        );
    }
}
