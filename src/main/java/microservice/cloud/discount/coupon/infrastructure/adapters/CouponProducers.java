package microservice.cloud.discount.coupon.infrastructure.adapters;

import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import microservice.cloud.discount.coupon.domain.event.CouponIsNotPublic;
import microservice.cloud.discount.coupon.domain.event.CouponPublished;
import reactor.core.publisher.Flux;

@Configuration
public class CouponProducers {

    @Bean
    public Supplier<Flux<CouponIsNotPublic>> couponIsNotPublic() {
        return Flux::empty;
    }

    @Bean
    public Supplier<Flux<CouponPublished>> couponPublished() {
        return Flux::empty;
    }
}
