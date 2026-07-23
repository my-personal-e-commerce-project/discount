package microservice.cloud.discount.coupon.domain.event;

import java.time.LocalDateTime;

import microservice.cloud.discount.shared.domain.event.DomainEvent;

public record CouponIsNotPublic(
    String aggregateId,
    String code,
    String discountId,
    LocalDateTime expiredAt,
    LocalDateTime occurredOn
) implements DomainEvent {

    public CouponIsNotPublic(
        String aggregateId,
        String code,
        String discountId,
        LocalDateTime expiredAt
    ) {
        this(
            aggregateId,
            code,
            discountId,
            expiredAt,
            LocalDateTime.now()
        );
    }

    public String domain() {
        return "discount.coupon";
    }
}
