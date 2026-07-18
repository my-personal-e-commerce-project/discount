package microservice.cloud.discount.coupon.domain.event;

import java.time.LocalDateTime;

import microservice.cloud.discount.shared.domain.event.DomainEvent;

public record CouponPublished(
    String aggregateId,
    String code,
    String couponId,
    LocalDateTime occurredOn
) implements DomainEvent {

    public CouponPublished(String aggregateId, String code, String couponId) {
        this(aggregateId, code, couponId, LocalDateTime.now());
    }
}
