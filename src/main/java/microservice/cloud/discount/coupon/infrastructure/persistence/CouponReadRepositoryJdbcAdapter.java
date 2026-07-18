package microservice.cloud.discount.coupon.infrastructure.persistence;

import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import microservice.cloud.discount.coupon.application.ports.dtos.CouponReadDTO;
import microservice.cloud.discount.coupon.application.ports.dtos.Query;
import microservice.cloud.discount.coupon.application.ports.out.CouponReadRepository;
import microservice.cloud.discount.shared.application.dto.Pagination;

@AllArgsConstructor
@Repository
public class CouponReadRepositoryJdbcAdapter implements CouponReadRepository {

    public Pagination<CouponReadDTO> findAll(Query query) {
        return null;
    }
}
