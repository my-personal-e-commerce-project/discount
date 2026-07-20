package microservice.cloud.discount.coupon.infrastructure.adapters;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import microservice.cloud.discount.coupon.application.ports.out.CouponReadRepository;
import microservice.cloud.discount.coupon.application.use_cases.BlockCouponUseCase;
import microservice.cloud.discount.coupon.application.use_cases.CreateCouponUseCase;
import microservice.cloud.discount.coupon.application.use_cases.DeleteCouponUseCase;
import microservice.cloud.discount.coupon.application.use_cases.ListCouponsUseCase;
import microservice.cloud.discount.coupon.application.use_cases.UpdateCouponUseCase;
import microservice.cloud.discount.coupon.domain.repository.CouponRepository;
import microservice.cloud.discount.shared.application.ports.out.EventPublisher;

@Configuration
public class CouponConfigAdapter {

    @Bean
    public ListCouponsUseCase listCouponsUseCase(CouponReadRepository couponReadRepository) {
        return new ListCouponsUseCase(couponReadRepository);
    }

    @Bean
    public BlockCouponUseCase blockedCouponUseCase(CouponRepository couponRepository, EventPublisher eventPublisher) {
        return new BlockCouponUseCase(couponRepository, eventPublisher);
    }

    @Bean
    public UpdateCouponUseCase updateCouponUseCase(
        CouponRepository couponRepository,
        EventPublisher eventPublisher
    ) {
        return new UpdateCouponUseCase(couponRepository, eventPublisher);
    }

    @Bean
    public DeleteCouponUseCase deleteCouponUseCase(CouponRepository couponRepository) {
        return new DeleteCouponUseCase(couponRepository);
    }

    @Bean
    public CreateCouponUseCase createCouponUseCase(CouponRepository couponRepository, EventPublisher eventPublisher) {
        return new CreateCouponUseCase(eventPublisher, couponRepository);
    }
}
