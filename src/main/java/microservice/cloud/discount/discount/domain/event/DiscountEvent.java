package microservice.cloud.discount.discount.domain.event;

import microservice.cloud.discount.shared.domain.event.DomainEvent;

public interface DiscountEvent extends DomainEvent {
    public String type();
}
