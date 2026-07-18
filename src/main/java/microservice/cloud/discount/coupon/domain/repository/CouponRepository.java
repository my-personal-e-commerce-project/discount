package microservice.cloud.discount.coupon.domain.repository;

import java.util.function.Consumer;

import microservice.cloud.discount.coupon.domain.entity.Coupon;
import microservice.cloud.discount.shared.domain.value_objects.Id;

public interface CouponRepository {

    public void create(Coupon coupon);
    public Coupon pessimisticUpdate(Id id,Consumer<Coupon> function);
    public void delete(Id id);
}
