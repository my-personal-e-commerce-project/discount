package microservice.cloud.discount.couponSales.infrastructure.adapters;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import microservice.cloud.discount.coupon.application.use_cases.BlockCouponUseCase;
import microservice.cloud.discount.coupon.domain.repository.CouponSalesRepository;
import microservice.cloud.discount.couponSales.application.use_cases.CreateCouponSalesUseCase;
import microservice.cloud.discount.couponSales.application.use_cases.IncrementCouponSalesUseCase;
import microservice.cloud.discount.couponSales.application.use_cases.UpdateCouponSalesUseCase;
import microservice.cloud.discount.shared.application.ports.out.EventPublisher;

@Configuration
public class CouponSalesConfigAdapter {

    @Bean
    public IncrementCouponSalesUseCase incrementCouponSalesUseCase(CouponSalesRepository couponSalesRepository, EventPublisher eventPublisher, BlockCouponUseCase blockCouponUseCase) {
        return new IncrementCouponSalesUseCase(couponSalesRepository, eventPublisher, blockCouponUseCase);
    }

    @Bean
    public UpdateCouponSalesUseCase updateCouponSalesUseCase(CouponSalesRepository couponSalesRepository) {
        return new UpdateCouponSalesUseCase(couponSalesRepository);
    }

    @Bean
    public CreateCouponSalesUseCase createCouponSalesUseCase(CouponSalesRepository couponSalesRepository) {
        return new CreateCouponSalesUseCase(couponSalesRepository);
    }
}
