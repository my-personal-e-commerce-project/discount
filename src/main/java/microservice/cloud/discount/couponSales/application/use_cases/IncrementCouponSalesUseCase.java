package microservice.cloud.discount.couponSales.application.use_cases;

import microservice.cloud.discount.coupon.domain.entity.Coupon;
import microservice.cloud.discount.coupon.domain.repository.CouponRepository;
import microservice.cloud.discount.couponSales.domain.entity.CouponSales;
import microservice.cloud.discount.couponSales.domain.repository.CouponSalesRepository;
import microservice.cloud.discount.shared.application.ports.out.EventPublisher;
import microservice.cloud.discount.shared.domain.value_objects.Id;

public class IncrementCouponSalesUseCase {
    private final CouponSalesRepository couponSalesRepository;
    private final CouponRepository couponRepository;
    private final EventPublisher eventPublisher;

    public IncrementCouponSalesUseCase(CouponSalesRepository couponSalesRepository, CouponRepository couponRepository, EventPublisher eventPublisher) {
        this.couponSalesRepository = couponSalesRepository;
        this.couponRepository = couponRepository;
        this.eventPublisher = eventPublisher;
    }

    public void execute(Id couponId) {
        CouponSales cs = couponSalesRepository.findByCouponId(couponId);

        Coupon coupon = couponRepository.findById(couponId);

        boolean isBlocked = coupon.maxSalesReached(cs.sales());

        if(isBlocked) coupon.block();
       
        cs.incrementSales();

        couponSalesRepository.updateIfExists(cs.id(), cs);

        if(!coupon.getEvents().isEmpty() && coupon.getEvents() != null) {
            eventPublisher.publish(coupon.getEvents());
        }
    }
}
