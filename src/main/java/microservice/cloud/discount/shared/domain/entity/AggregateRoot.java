package microservice.cloud.discount.shared.domain.entity;

import java.util.ArrayList;
import java.util.List;

import microservice.cloud.discount.shared.domain.event.DomainEvent;

public abstract class AggregateRoot {
    private List<DomainEvent> events = new ArrayList<>();

    protected void publishEvent(DomainEvent event) {

        events.add(event);
    };

    public List<DomainEvent> getEvents() {
        return new ArrayList<>(events);
    }
}
