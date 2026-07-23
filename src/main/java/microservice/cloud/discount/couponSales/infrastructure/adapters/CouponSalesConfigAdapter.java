package microservice.cloud.discount.couponSales.infrastructure.adapters;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import microservice.cloud.discount.coupon.domain.repository.CouponRepository;
import microservice.cloud.discount.couponSales.domain.repository.CouponSalesRepository;
import microservice.cloud.discount.couponSales.application.use_cases.IncrementCouponSalesUseCase;
import microservice.cloud.discount.shared.application.ports.out.EventPublisher;

@Configuration
public class CouponSalesConfigAdapter {

    @Bean
    public IncrementCouponSalesUseCase incrementCouponSalesUseCase(CouponSalesRepository couponSalesRepository, CouponRepository couponRepository, EventPublisher eventPublisher) {
        return new IncrementCouponSalesUseCase(couponSalesRepository, couponRepository, eventPublisher);
    }
}
