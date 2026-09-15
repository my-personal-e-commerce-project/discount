package microservice.cloud.discount.shared.infrastructure.adapters.in;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.cloud.discount.discount.application.event.CategoryDiscountRemoved;
import microservice.cloud.discount.discount.application.use_cases.RemoveDiscountCategoriesLogUseCase;
import microservice.cloud.discount.shared.application.ports.out.DomainOutboxDaoInterface;
import microservice.cloud.discount.shared.domain.value_objects.Id;
import microservice.cloud.discount.shared.infrastructure.dto.DeletedCategory;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class KafkaConsumer {
  
    private final ObjectMapper objectMapper;
    private final RemoveDiscountCategoriesLogUseCase removeDiscountCategoriesLogUseCase;
    private final DomainOutboxDaoInterface domainOutboxDao;

    @Bean
    public Consumer<DeletedCategory> processInventorySaga() {
        return inputEvent -> {
            String aggregateId = inputEvent.aggregateId();
            CategoryDiscountRemoved outputEvent;

            try {
                removeDiscountCategoriesLogUseCase.execute(
                    Id.fromString(aggregateId)
                );

                outputEvent = new CategoryDiscountRemoved(
                    aggregateId
                );
            } catch (Exception e) {
                log.error("Error processing DeletedCategory event", e.getMessage());
                outputEvent = new CategoryDiscountRemoved(
                    aggregateId,
                    false,
                    e.getMessage()
                );
            }

            try {
                String payload = objectMapper.writeValueAsString(outputEvent);
                domainOutboxDao.save(outputEvent.topic(), payload);
            }catch(JsonProcessingException e) {
                log.error("Serializer error", e.getMessage());
            }
        };
    }
}
