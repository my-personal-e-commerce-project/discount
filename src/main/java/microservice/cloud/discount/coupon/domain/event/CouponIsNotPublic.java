package microservice.cloud.discount.coupon.domain.event;

import java.time.LocalDateTime;

import microservice.cloud.discount.shared.domain.event.DomainEvent;

public record CouponIsNotPublic(
    String aggregateId,
    LocalDateTime occurredOn
) implements DomainEvent {

    public CouponIsNotPublic(String aggregateId) {
        this(aggregateId, LocalDateTime.now());
    }
}
