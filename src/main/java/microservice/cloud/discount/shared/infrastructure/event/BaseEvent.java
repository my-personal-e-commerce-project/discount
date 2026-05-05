package microservice.cloud.discount.shared.infrastructure.event;

import java.time.Instant;

public interface BaseEvent {
    String eventName();
    String aggregateId();
    Instant occurredOn();
}
