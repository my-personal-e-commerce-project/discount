package microservice.cloud.discount.discount.infrastrcture.adapters.out;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import microservice.cloud.discount.discount.domain.event.DiscountEvent;

@RequiredArgsConstructor
@Component
public class SendDiscountEvent {

    private final StreamBridge streamBridge;

    public void send(DiscountEvent event) {
        streamBridge.send("sendDiscountEvent-out-0", event);
    }
}
