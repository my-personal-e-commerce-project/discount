package microservice.cloud.discount.coupon.application.use_cases;

import java.time.LocalDateTime;

import microservice.cloud.discount.coupon.domain.entity.Coupon;
import microservice.cloud.discount.coupon.domain.repository.CouponRepository;
import microservice.cloud.discount.coupon.domain.value_objects.CouponCode;
import microservice.cloud.discount.coupon.domain.value_objects.CouponVisibility;
import microservice.cloud.discount.shared.application.ports.out.EventPublisher;
import microservice.cloud.discount.shared.application.ports.out.GetMePort;
import microservice.cloud.discount.shared.domain.value_objects.Id;
import microservice.cloud.discount.shared.domain.value_objects.Me;
import microservice.cloud.discount.shared.domain.value_objects.Permission;

public class UpdateCouponUseCase {
    private final CouponRepository couponRepository;
    private final EventPublisher eventPublisher;
    private final GetMePort getMePort;

    public UpdateCouponUseCase(
        CouponRepository couponRepository,
        EventPublisher eventPublisher,
        GetMePort getMePort
    ) {
        this.couponRepository = couponRepository;
        this.eventPublisher = eventPublisher;
        this.getMePort = getMePort;
    }

    public void execute(
        Id id,
        Id discountId,
        CouponCode code,
        Integer maxSales,
        CouponVisibility visibility,
        LocalDateTime expiredAt
    ) {
        Me me = getMePort.execute();

        if(me == null)
            throw new RuntimeException("You do not have permission to perform this action");

        me.IHavePermission(Permission.createCoupon());

        Coupon coupon = couponRepository.findById(id);

        coupon.update(
            discountId,
            code,
            maxSales,
            visibility,
            expiredAt
        );

        couponRepository.update(coupon);

        if(!coupon.getEvents().isEmpty()) {
            eventPublisher.publish(coupon.getEvents());
        }
    }
}
