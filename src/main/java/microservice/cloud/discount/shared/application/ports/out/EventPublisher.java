package microservice.cloud.discount.shared.application.ports.out;

import java.util.List;

public interface EventPublisher {

    public void publish(List<? extends Object> events);
}
