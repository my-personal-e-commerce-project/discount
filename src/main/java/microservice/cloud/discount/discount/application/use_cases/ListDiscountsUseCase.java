package microservice.cloud.discount.discount.application.use_cases;

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

    public Pagination<DiscountReadDTO> execute(int page, int size) {
        return discountReadRepository.listDiscounts(page, size);
    }
}
