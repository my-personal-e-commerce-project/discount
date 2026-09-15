package microservice.cloud.discount.shared.application.event;

import java.time.LocalDateTime;

public interface Event {
    LocalDateTime occurredOn();
    String topic();
}
