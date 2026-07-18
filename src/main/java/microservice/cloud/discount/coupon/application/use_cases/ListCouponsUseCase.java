package microservice.cloud.discount.coupon.application.use_cases;

import microservice.cloud.discount.coupon.application.ports.dtos.CouponReadDTO;
import microservice.cloud.discount.coupon.application.ports.dtos.Query;
import microservice.cloud.discount.coupon.application.ports.out.CouponReadRepository;
import microservice.cloud.discount.shared.application.dto.Pagination;

public class ListCouponsUseCase {
    private final CouponReadRepository couponReadRepository;

    public ListCouponsUseCase(CouponReadRepository couponReadRepository) {
        this.couponReadRepository = couponReadRepository;
    }

    public Pagination<CouponReadDTO> execute(Query query) {
        return couponReadRepository.findAll(query);
    }
}
