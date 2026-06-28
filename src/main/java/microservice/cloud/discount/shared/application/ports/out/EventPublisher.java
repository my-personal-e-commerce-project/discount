package microservice.cloud.discount.shared.application.ports.out;

import java.util.List;

import microservice.cloud.discount.shared.domain.event.DomainEvent;

public interface EventPublisher {

    public void publish(List<? extends DomainEvent> events);
}
