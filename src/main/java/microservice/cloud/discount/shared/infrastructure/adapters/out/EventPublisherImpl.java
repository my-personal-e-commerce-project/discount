package microservice.cloud.discount.shared.infrastructure.adapters.out;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.cloud.discount.discount.application.event.CategoryDiscountRemovalFailed;
import microservice.cloud.discount.discount.application.event.CategoryDiscountRemoved;
import microservice.cloud.discount.shared.application.ports.out.EventPublisher;

@Slf4j
@RequiredArgsConstructor
@Component
public class EventPublisherImpl implements EventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publish(List<? extends Object> events) {
        if (events == null || events.isEmpty()) return;
   
        events.forEach(applicationEventPublisher::publishEvent);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleDeletedCategory(CategoryDiscountRemoved event) throws RuntimeException {
        try {
            String messageKey = String.valueOf(event.categoryId()); 
            
            kafkaTemplate.send("discount.discount.saga-events", messageKey, event);
        } catch (Exception e) {
            log.error(
                "CRITICAL: CategoryDiscountRemoved event could not be sent via Kafka: " + e.getMessage()
            );
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleDeletedCategory(CategoryDiscountRemovalFailed event) throws RuntimeException {
        try {
            String messageKey = String.valueOf(event.categoryId()); 
            
            kafkaTemplate.send("discount.discount.saga-failed-events", messageKey, event);
        } catch (Exception e) {
            log.error(
                "CRITICAL: CategoryDiscountRemovalFailed event could not be sent via Kafka: " + e.getMessage()
            );
        }
    }
}
