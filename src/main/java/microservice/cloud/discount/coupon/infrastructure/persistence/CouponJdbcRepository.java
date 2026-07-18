package microservice.cloud.discount.coupon.infrastructure.persistence;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface CouponJdbcRepository extends Repository<CouponEntity, String> {

    @Query("SELECT * FROM coupon WHERE id = :id FOR UPDATE")
    Optional<CouponEntity> findByIdForUpdate(String id);
    
    CouponEntity save(CouponEntity coupon);
    void deleteById(String id);
}
