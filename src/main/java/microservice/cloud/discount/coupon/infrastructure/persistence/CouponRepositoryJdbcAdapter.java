package microservice.cloud.discount.coupon.infrastructure.persistence;

import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
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
    private final JdbcAggregateTemplate jdbcAggregateTemplate;
    private final DiscountJdbcRepository discountJdbcRepository;

    @Transactional(readOnly = true)
    @Override
    public Coupon findById(Id id) {
        CouponEntity entity = couponJdbcRepository.findById(id.value())
            .orElseThrow(
                () -> new DataNotFound("Coupon not found")
            );

        return toMap(entity);
    }

    @Override
    @Transactional
    public void update(Coupon coupon) {
        if(!discountJdbcRepository.existsById(coupon.discountId().value())) {
            throw new DataNotFound("Discount not found");
        }
        
        couponJdbcRepository.save(toMap(coupon));
    }

    @Transactional
    @Override
    public void create(Coupon coupon) {
        if(couponJdbcRepository.existsByCode(coupon.code().value())) {
            throw new RuntimeException("Coupon code already exists");
        }

        if(!discountJdbcRepository.existsById(coupon.discountId().value())) {
            throw new DataNotFound("Discount not found");
        }

        jdbcAggregateTemplate.insert(toMap(coupon));
    }

    @Transactional
    @Override
    public void deleteIfExists(Id id) {
        couponJdbcRepository.findById(id.value()).orElseThrow(
            () -> new DataNotFound("Coupon not found")
        );

        couponJdbcRepository.deleteById(id.value());
    }

    private Coupon toMap(CouponEntity entity) {
        return new Coupon(
            Id.fromString(entity.getId()),
            Id.fromString(entity.getDiscountId()),
            Id.fromString(entity.getCouponsSalesId()),
            new CouponCode(entity.getCode()),
            entity.getMaxSales(),
            CouponVisibility.valueOf(entity.getVisibility()),
            entity.getExpiredAt()
        );
    }

    private CouponEntity toMap(Coupon coupon) {
        return new CouponEntity(
            coupon.id().value(),
            coupon.discountId().value(),
            coupon.couponSalesId().value(),
            coupon.code().value(),
            coupon.maxSales(),
            coupon.visibility().toString(),
            coupon.expiredAt()
        );
    }
}
