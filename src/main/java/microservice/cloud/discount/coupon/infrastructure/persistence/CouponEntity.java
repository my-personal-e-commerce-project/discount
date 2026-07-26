package microservice.cloud.discount.coupon.infrastructure.persistence;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import microservice.cloud.discount.coupon.domain.entity.Coupon;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table("coupons")
public class CouponEntity {

    @Id
    private String id;
    private String discountId;
    private String code;
    private Integer maxSales;
    private String visibility;
    private LocalDateTime expiredAt;

    @Version
    private Long version;

    public void updateFromDomain(Coupon coupon) {
        this.discountId = coupon.discountId().value();
        this.code = coupon.code().value();
        this.maxSales = coupon.maxSales();
        this.visibility = coupon.visibility().name();
        this.expiredAt = coupon.expiredAt();
    }
}
