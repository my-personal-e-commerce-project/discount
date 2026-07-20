package microservice.cloud.discount.couponSales.application.use_cases;

import java.util.List;

import microservice.cloud.discount.coupon.application.use_cases.BlockCouponUseCase;
import microservice.cloud.discount.coupon.domain.repository.CouponSalesRepository;
import microservice.cloud.discount.couponSales.domain.entity.CouponSales;
import microservice.cloud.discount.couponSales.domain.event.CouponSalesLimitReached;
import microservice.cloud.discount.shared.application.ports.out.EventPublisher;
import microservice.cloud.discount.shared.domain.event.DomainEvent;
import microservice.cloud.discount.shared.domain.value_objects.Id;

public class IncrementCouponSalesUseCase {
    private final CouponSalesRepository couponSalesRepository;
    private final EventPublisher eventPublisher;
    private final BlockCouponUseCase blockedCouponUseCase;

    public IncrementCouponSalesUseCase(CouponSalesRepository couponSalesRepository, EventPublisher eventPublisher, BlockCouponUseCase blockCouponUseCase) {
        this.couponSalesRepository = couponSalesRepository;
        this.eventPublisher = eventPublisher;
        this.blockedCouponUseCase = blockCouponUseCase;
    }

    public void execute(Id id) {
        CouponSales entity = couponSalesRepository.pessimisticUpdate(id, CouponSales::incrementSales);

        List<DomainEvent> events = entity.getEvents();
        if (events != null && !events.isEmpty()) {
            if (events.stream().anyMatch(e -> e instanceof CouponSalesLimitReached)) {
                blockedCouponUseCase.execute(entity.couponId());
            }

            eventPublisher.publish(events);
        }
    }
}
