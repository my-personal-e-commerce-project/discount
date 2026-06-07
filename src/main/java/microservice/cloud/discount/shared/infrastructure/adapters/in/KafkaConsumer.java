
package microservice.cloud.discount.shared.infrastructure.adapters.in;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import lombok.RequiredArgsConstructor;
import microservice.cloud.discount.discount.application.use_cases.RemoveDiscountCategoriesLogUseCase;
import microservice.cloud.discount.shared.domain.value_objects.Id;
import microservice.cloud.discount.shared.infrastructure.dto.DeletedCategory;

@RequiredArgsConstructor
@Configuration
public class KafkaConsumer {

    private final RemoveDiscountCategoriesLogUseCase removeDiscountCategoriesLogUseCase;
    
    @Bean
    public Consumer<Message<DeletedCategory>> deletedCategorySagaHandler() {
        return message -> {
            System.out.println("deletedCategorySagaHandler");
            removeDiscountCategoriesLogUseCase.execute(
                Id.fromString(
                    message.getPayload().aggregateId()
                )
            );
        };
    }
}
