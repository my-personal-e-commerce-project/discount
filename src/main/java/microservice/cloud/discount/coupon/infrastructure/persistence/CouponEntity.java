package microservice.cloud.discount.coupon.infrastructure.persistence;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table("coupons")
public class CouponEntity {

    @Id
    private String id;
    private String discountId;
    private Integer sales;
    private String code;
    private String visibility;
    private Integer maxSales;
    private LocalDateTime expiredAt;
}
