package microservice.cloud.discount.shared.infrastructure.adapters.out;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import microservice.cloud.discount.shared.application.ports.out.EventPublisher;
import microservice.cloud.discount.shared.domain.event.DomainEvent;

@RequiredArgsConstructor
@Component
public class EventPublisherImpl implements EventPublisher {

    @Override
    public void publish(List<? extends DomainEvent> events) {

    }
}
