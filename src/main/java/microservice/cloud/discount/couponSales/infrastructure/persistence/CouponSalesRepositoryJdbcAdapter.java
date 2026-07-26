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
    public void updateIfExists(Id id, CouponSales couponSales) {
        CouponSalesEntity cs = jdbcAggregateTemplate.findById(id.value(), CouponSalesEntity.class);
        
        cs.updateFromDomain(couponSales);

        jdbcAggregateTemplate.update(cs);
    }

    @Transactional(readOnly = true)
    public CouponSales findByCouponId(Id couponId) {
        String sql = "SELECT * FROM coupons_sales WHERE coupon_id = :couponId";
        MapSqlParameterSource params = new MapSqlParameterSource("couponId", couponId);

        List<CouponSalesEntity> result = namedParameterJdbcTemplate.query(
            sql, 
            params, 
            new BeanPropertyRowMapper<>(CouponSalesEntity.class)
        );

        CouponSalesEntity coupon = result.stream()
            .findFirst()
            .orElseThrow(() -> new DataNotFound("Coupon sales not found"));

        return toMap(coupon);
    }

    public CouponSales toMap(CouponSalesEntity couponSalesEntity) {
        return new CouponSales(
            Id.fromString(couponSalesEntity.getId()),
            Id.fromString(couponSalesEntity.getCouponId()),
            couponSalesEntity.getSales()
        );
    }
}
