package microservice.cloud.discount.couponSales.application.use_cases;

import microservice.cloud.discount.couponSales.domain.repository.CouponSalesRepository;
import microservice.cloud.discount.couponSales.domain.entity.CouponSales;
import microservice.cloud.discount.shared.application.ports.out.GetMePort;
import microservice.cloud.discount.shared.domain.value_objects.Id;
import microservice.cloud.discount.shared.domain.value_objects.Me;
import microservice.cloud.discount.shared.domain.value_objects.Permission;

public class CreateCouponSalesUseCase {
    private final CouponSalesRepository couponSalesRepository;
    private final GetMePort getMePort;

    public CreateCouponSalesUseCase(CouponSalesRepository couponSalesRepository, GetMePort getMePort) {
        this.couponSalesRepository = couponSalesRepository;
        this.getMePort = getMePort;
    }
    
    public void execute(Id id) {
        Me me = getMePort.execute();

        if(me == null)
            throw new RuntimeException("You do not have permission to perform this action");

        me.IHavePermission(Permission.createCoupon());

        CouponSales couponSales = CouponSales.factoryCouponSales(id);

        couponSalesRepository.create(couponSales);
    }
}
