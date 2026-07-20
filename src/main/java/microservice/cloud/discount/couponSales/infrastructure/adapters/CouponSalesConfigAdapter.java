package microservice.cloud.discount.couponSales.infrastructure.adapters;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import microservice.cloud.discount.coupon.application.use_cases.BlockCouponUseCase;
import microservice.cloud.discount.coupon.domain.repository.CouponRepository;
import microservice.cloud.discount.couponSales.domain.repository.CouponSalesRepository;
import microservice.cloud.discount.couponSales.application.use_cases.CreateCouponSalesUseCase;
import microservice.cloud.discount.couponSales.application.use_cases.IncrementCouponSalesUseCase;
import microservice.cloud.discount.shared.application.ports.out.GetMePort;

@Configuration
public class CouponSalesConfigAdapter {

    @Bean
    public IncrementCouponSalesUseCase incrementCouponSalesUseCase(CouponSalesRepository couponSalesRepository, BlockCouponUseCase blockCouponUseCase, CouponRepository couponRepository) {
        return new IncrementCouponSalesUseCase(couponSalesRepository, blockCouponUseCase, couponRepository);
    }

    @Bean
    public CreateCouponSalesUseCase createCouponSalesUseCase(CouponSalesRepository couponSalesRepository, GetMePort getMePort){
        return new CreateCouponSalesUseCase(couponSalesRepository, getMePort);
    }
}
