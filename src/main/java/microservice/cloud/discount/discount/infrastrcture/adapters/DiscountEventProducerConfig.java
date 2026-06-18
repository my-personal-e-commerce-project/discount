package microservice.cloud.discount.discount.infrastrcture.adapters;

import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import microservice.cloud.discount.discount.application.event.CategoryDiscountRemoved;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class DiscountEventProducerConfig {
    private final Sinks.Many<CategoryDiscountRemoved> processor = Sinks.many().unicast().onBackpressureBuffer();

    @Bean
    public Supplier<Flux<CategoryDiscountRemoved>> deletedCategoryProducer() {
        return () -> processor.asFlux();
    }

    public void categoryDiscountRemovedProducer(CategoryDiscountRemoved event) {
        processor.emitNext(event, Sinks.EmitFailureHandler.FAIL_FAST);
    }
}
