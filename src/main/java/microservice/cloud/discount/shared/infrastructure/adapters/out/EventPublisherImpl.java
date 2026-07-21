package microservice.cloud.discount.shared.infrastructure.adapters.out;

import java.util.List;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import microservice.cloud.discount.coupon.domain.event.CouponIsNotPublic;
import microservice.cloud.discount.coupon.domain.event.CouponPublished;
import microservice.cloud.discount.shared.application.ports.out.EventPublisher;
import microservice.cloud.discount.shared.domain.event.DomainEvent;

@RequiredArgsConstructor
@Component
public class EventPublisherImpl implements EventPublisher {

    private final StreamBridge streamBridge;

    @Override
    public void publish(List<? extends DomainEvent> events) {
        events.forEach(e -> {
            if (e instanceof CouponIsNotPublic)
                publishCouponNotPublic((CouponIsNotPublic) e);

            if (e instanceof CouponPublished)
                publishCouponPublished((CouponPublished) e);
        });
    }

    public void publishCouponNotPublic(CouponIsNotPublic event) {
        streamBridge.send("couponIsNotPublic-out-0", event);
    }

    public void publishCouponPublished(CouponPublished event) {
        streamBridge.send("couponPublished-out-0", event);
    }
}
