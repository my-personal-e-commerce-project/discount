package microservice.cloud.discount.coupon.domain.repository;

import microservice.cloud.discount.coupon.domain.entity.Coupon;
import microservice.cloud.discount.shared.domain.value_objects.Id;

public interface CouponRepository {

    public Coupon findById(Id id);
    public void create(Coupon coupon);
    public void update(Coupon coupon);
    public void delete(Id id);
}
