package microservice.cloud.discount.discount.application.event;

import java.time.LocalDateTime;

import microservice.cloud.discount.shared.application.event.Event;

public record CategoryDiscountRemoved (
    String categoryId,
    boolean success,
    String errorMessage,
    LocalDateTime occurredOn
) implements Event {

    public CategoryDiscountRemoved(String categoryId) {
        this(categoryId, true, null, LocalDateTime.now());
    }

    public CategoryDiscountRemoved(String categoryId, boolean success, String message) {
        this(categoryId, success, message, LocalDateTime.now());
    }

    @Override
    public LocalDateTime occurredOn() {
        return occurredOn;
    }
}
