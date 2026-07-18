package microservice.cloud.discount.coupon.infrastructure.persistence;

import java.util.Optional;

import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface CouponJdbcRepository extends Repository<CouponEntity, String> {

    CouponEntity save(CouponEntity coupon);
    void deleteById(String id);
    boolean existsByCode(String code);
    Optional<CouponEntity> findById(String id);
}
