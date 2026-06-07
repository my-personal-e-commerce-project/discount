package microservice.cloud.discount.discount.application.use_cases;

import java.util.List;

import microservice.cloud.discount.discount.application.event.CategoryDiscountRemovalFailed;
import microservice.cloud.discount.discount.application.event.CategoryDiscountRemoved;
import microservice.cloud.discount.discount.domain.repository.DiscountRepository;
import microservice.cloud.discount.shared.application.ports.out.EventPublisher;
import microservice.cloud.discount.shared.domain.value_objects.Id;

public class RemoveDiscountCategoriesLogUseCase {

    private final DiscountRepository discountRepository;
    private final EventPublisher eventPublisher;

    public RemoveDiscountCategoriesLogUseCase(
        DiscountRepository discountRepository,
        EventPublisher eventPublisher
    ) {
        this.discountRepository = discountRepository;
        this.eventPublisher = eventPublisher;
    }

    public void execute(Id categoryId) {
        try {
            discountRepository.removeDiscountCategoriesLog(categoryId);
        } catch (RuntimeException e) {
            CategoryDiscountRemovalFailed categoryDiscountRemovalFailed = new CategoryDiscountRemovalFailed(categoryId.value());
            eventPublisher.publish(List.of(categoryDiscountRemovalFailed));
            throw e;
        }

        CategoryDiscountRemoved categoryDiscountRemoved = new CategoryDiscountRemoved(categoryId.value());
        eventPublisher.publish(List.of(categoryDiscountRemoved));
    }
}
