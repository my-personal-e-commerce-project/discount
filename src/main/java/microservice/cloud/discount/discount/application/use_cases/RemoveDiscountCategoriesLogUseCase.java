package microservice.cloud.discount.discount.application.use_cases;

import microservice.cloud.discount.discount.domain.repository.DiscountRepository;
import microservice.cloud.discount.shared.domain.value_objects.Id;

public class RemoveDiscountCategoriesLogUseCase {

    private final DiscountRepository discountRepository;

    public RemoveDiscountCategoriesLogUseCase(
        DiscountRepository discountRepository
    ) {
        this.discountRepository = discountRepository;
    }

    public void execute(Id categoryId) {
        discountRepository.removeDiscountCategoriesLog(categoryId);
    }
}
