package microservice.cloud.discount.coupon.application.ports.out;

import microservice.cloud.discount.coupon.application.ports.dtos.CouponReadDTO;
import microservice.cloud.discount.coupon.application.ports.dtos.Query;
import microservice.cloud.discount.shared.application.dto.Pagination;

public interface CouponReadRepository {
    public Pagination<CouponReadDTO> findAll(Query query);
}
