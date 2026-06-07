package microservice.cloud.discount.shared.infrastructure.dto;

import java.time.LocalDateTime;

import microservice.cloud.discount.shared.domain.event.DomainEvent;

public record DeletedCategory(
    LocalDateTime occurredOn,
    String aggregateId
) implements DomainEvent {

    public DeletedCategory(
        String aggregateId
    ) {
        this(
            LocalDateTime.now(),
            aggregateId
        );
    }
}
