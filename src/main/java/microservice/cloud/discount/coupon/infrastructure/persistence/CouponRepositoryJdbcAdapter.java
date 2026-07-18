package microservice.cloud.discount.coupon.infrastructure.persistence;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import microservice.cloud.discount.coupon.domain.entity.Coupon;
import microservice.cloud.discount.coupon.domain.repository.CouponRepository;
import microservice.cloud.discount.coupon.domain.value_objects.CouponCode;
import microservice.cloud.discount.coupon.domain.value_objects.CouponVisibility;
import microservice.cloud.discount.discount.infrastrcture.persistence.repository.DiscountJdbcRepository;
import microservice.cloud.discount.shared.domain.exception.DataNotFound;
import microservice.cloud.discount.shared.domain.value_objects.Id;

@RequiredArgsConstructor
@Repository
public class CouponRepositoryJdbcAdapter implements CouponRepository{

    private final CouponJdbcRepository couponJdbcRepository;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcAggregateTemplate jdbcAggregateTemplate;
    private final DiscountJdbcRepository discountJdbcRepository;

    @Override
    @Transactional
    public Coupon pessimisticUpdate(Id id, Consumer<Coupon> function) {
        Coupon coupon = findByIdForUpdate(id.value());
        function.accept(coupon);
        couponJdbcRepository.save(toMap(coupon));

        return coupon;
    }

    private Coupon findByIdForUpdate(String id) {
        String sql = "SELECT * FROM coupons WHERE id = :id FOR UPDATE";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        List<CouponEntity> result = namedParameterJdbcTemplate.query(
            sql, 
            params, 
            new BeanPropertyRowMapper<>(CouponEntity.class)
        );

        CouponEntity coupon = result.stream()
            .findFirst()
            .orElseThrow(() -> new DataNotFound("Coupon not found"));

        return toMap(coupon);
    }

    @Transactional
    @Override
    public void create(Coupon coupon) {
        if(couponJdbcRepository.existsByCode(coupon.code().value())) {
            throw new RuntimeException("Coupon code already exists");
        }

        if(discountJdbcRepository.existsById(coupon.discountId().value())) {
            throw new DataNotFound("Discount not found");
        }

        jdbcAggregateTemplate.insert(toMap(coupon));
    }

    @Transactional
    @Override
    public void delete(Id id) {
        CouponEntity entity = couponJdbcRepository.findById(id.value()).orElse(null);

        if(entity == null) {
            throw new DataNotFound("Coupon not found");
        }

        if(discountJdbcRepository.existsById(entity.getDiscountId())) {
            throw new DataNotFound("Discount not found");
        }

        if(couponJdbcRepository.existsByCode(entity.getCode())) {
            throw new RuntimeException("Coupon code already exists");
        }

        couponJdbcRepository.deleteById(id.value());
    }

    private Coupon toMap(CouponEntity entity) {
        return new Coupon(
            Id.fromString(entity.getId()),
            Id.fromString(entity.getDiscountId()),
            new CouponCode(entity.getCode()),
            CouponVisibility.valueOf(entity.getVisibility()),
            entity.getSales(),
            entity.getMaxSales(),
            entity.getExpiredAt()
        );
    }

    private CouponEntity toMap(Coupon coupon) {
        return new CouponEntity(
            coupon.id().value(),
            coupon.discountId().value(),
            coupon.sales(),
            coupon.code().value(),
            coupon.visibility().toString(),
            coupon.maxSales(),
            coupon.expiredAt()
        );
    }
}
