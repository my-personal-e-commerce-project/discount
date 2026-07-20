package microservice.cloud.discount.couponSales.infrastructure.persistence;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import microservice.cloud.discount.couponSales.domain.repository.CouponSalesRepository;
import microservice.cloud.discount.couponSales.domain.entity.CouponSales;
import microservice.cloud.discount.shared.domain.exception.DataNotFound;
import microservice.cloud.discount.shared.domain.value_objects.Id;

@RequiredArgsConstructor
@Repository
public class CouponSalesRepositoryJdbcAdapter implements CouponSalesRepository {

    private final JdbcAggregateTemplate jdbcAggregateTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Transactional
    @Override
    public CouponSales pessimisticUpdate(Id discountId, Consumer<CouponSales> function) {
        CouponSales couponSales = findByIdForUpdate(discountId.value());
        function.accept(couponSales);
        jdbcAggregateTemplate.update(toMap(couponSales));
        
        return null;
    }
 
    private CouponSales findByIdForUpdate(String id) {
        String sql = "SELECT * FROM coupons_sales WHERE id = :id FOR UPDATE";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        List<CouponSalesEntity> result = namedParameterJdbcTemplate.query(
            sql, 
            params, 
            new BeanPropertyRowMapper<>(CouponSalesEntity.class)
        );

        CouponSalesEntity coupon = result.stream()
            .findFirst()
            .orElseThrow(() -> new DataNotFound("Coupon not found"));

        return toMap(coupon);
    }

    @Transactional
    @Override
    public void create(CouponSales couponSales) {
        jdbcAggregateTemplate.insert(toMap(couponSales));
    }

    public CouponSales toMap(CouponSalesEntity couponSalesEntity) {
        return new CouponSales(
            Id.fromString(couponSalesEntity.getId()),
            couponSalesEntity.getSales()
        );
    }

    public CouponSalesEntity toMap(CouponSales couponSales) {
        return new CouponSalesEntity(
            couponSales.id().value(),
            couponSales.sales()
        );
        
    }
}
