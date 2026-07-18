package microservice.cloud.discount.coupon.infrastructure.persistence;

import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface CouponJdbcRepository extends Repository<CouponEntity, String> {

    CouponEntity save(CouponEntity coupon);
    void deleteById(String id);
    boolean existsById(String id);
}
