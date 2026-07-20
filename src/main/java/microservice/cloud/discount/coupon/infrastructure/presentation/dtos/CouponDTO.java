package microservice.cloud.discount.coupon.infrastructure.presentation.dtos;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class CouponDTO{
    @Setter
    private String id;
    @NotEmpty
    private String discountId;
    private Integer sales;
    @NotEmpty
    private String code;
    @Pattern(regexp = "PUBLIC|PRIVATE|BLOCKED", message = "The value must be PUBLIC, PRIVATE or BLOCKED")
    @NotEmpty
    private String visibility;
    private Integer maxSales;
    @Future
    private LocalDateTime expiredAt;
}
