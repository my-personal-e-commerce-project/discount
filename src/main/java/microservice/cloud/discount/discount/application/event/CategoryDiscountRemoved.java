package microservice.cloud.discount.discount.application.event;

import java.time.LocalDateTime;

import microservice.cloud.discount.shared.application.event.Event;

public record CategoryDiscountRemoved (
    String categoryId,
    LocalDateTime occurredOn
) implements Event {

    public CategoryDiscountRemoved(String categoryId) {
        this(categoryId, LocalDateTime.now());
    }

    @Override
    public LocalDateTime occurredOn() {
        return occurredOn;
    }
}
