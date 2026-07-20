package microservice.cloud.discount.coupon.application.use_cases;

import microservice.cloud.discount.coupon.domain.repository.CouponRepository;
import microservice.cloud.discount.shared.application.ports.out.GetMePort;
import microservice.cloud.discount.shared.domain.value_objects.Id;
import microservice.cloud.discount.shared.domain.value_objects.Me;
import microservice.cloud.discount.shared.domain.value_objects.Permission;

public class DeleteCouponUseCase {
    private final CouponRepository couponRepository;
    private final GetMePort getMePort;

    public DeleteCouponUseCase(CouponRepository couponRepository, GetMePort getMePort) {
        this.couponRepository = couponRepository;
        this.getMePort = getMePort;
    }

    public void execute(Id id) {
        Me me = getMePort.execute();

        if(me == null)
            throw new RuntimeException("You do not have permission to perform this action");

        me.IHavePermission(Permission.deleteCoupon());

        couponRepository.deleteIfExists(id);
    }
}
