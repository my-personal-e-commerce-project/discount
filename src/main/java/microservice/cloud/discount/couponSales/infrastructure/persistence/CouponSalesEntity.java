package microservice.cloud.discount.couponSales.infrastructure.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("coupons_sales")
public class CouponSalesEntity {

    @Id
    private String id;
    private String couponId;
    private Integer sales;
}
