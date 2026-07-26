package microservice.cloud.discount.couponSales.infrastructure.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import microservice.cloud.discount.couponSales.domain.entity.CouponSales;

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

    @Version
    private Long version;

    public void updateFromDomain(CouponSales cs) {
        this.id = cs.id().value();
        this.couponId = cs.couponId().value();
        this.sales = cs.sales();

        this.version++;
    } 
}
