package microservice.cloud.discount.couponSales.domain.event;

import java.time.LocalDateTime;

import microservice.cloud.discount.shared.domain.event.DomainEvent;

public record CouponSalesLimitReached(
    String aggregateId,
    LocalDateTime occurredOn
) implements DomainEvent {

    public CouponSalesLimitReached(String aggregateId) {
        this(aggregateId, LocalDateTime.now());
    }

    public String domain() {
        return "discount.couponSales";
    }
}
