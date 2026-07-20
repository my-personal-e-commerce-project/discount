package microservice.cloud.discount.couponSales.infrastructure.persistence;

import java.util.function.Consumer;

import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import microservice.cloud.discount.coupon.domain.repository.CouponSalesRepository;
import microservice.cloud.discount.couponSales.domain.entity.CouponSales;
import microservice.cloud.discount.shared.domain.value_objects.Id;

@RequiredArgsConstructor
@Repository
public class CouponSalesRepositoryJdbcAdapter implements CouponSalesRepository {

    private final JdbcAggregateTemplate jdbcAggregateTemplate;

    @Override
    public CouponSales pessimisticUpdate(Id discountId, Consumer<CouponSales> function) {

        // TODO Auto-generated method stub
        return null;
    }
  
    @Override
    public void create(CouponSales couponSales) {
        jdbcAggregateTemplate.insert(toMap(couponSales));
    }

    public CouponSalesEntity toMap(CouponSales couponSales) {
        return new CouponSalesEntity(
            couponSales.id().value(),
            couponSales.couponId().value(),
            couponSales.sales(),
            couponSales.maxSales()
        );
        
    }
}
