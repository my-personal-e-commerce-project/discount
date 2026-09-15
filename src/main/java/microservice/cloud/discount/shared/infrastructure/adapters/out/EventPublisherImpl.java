package microservice.cloud.discount.shared.infrastructure.adapters.out;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.cloud.discount.coupon.domain.event.CouponIsNotPublic;
import microservice.cloud.discount.coupon.domain.event.CouponPublished;
import microservice.cloud.discount.shared.application.ports.out.DomainOutboxDaoInterface;
import microservice.cloud.discount.shared.application.ports.out.EventPublisher;
import microservice.cloud.discount.shared.domain.event.DomainEvent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@RequiredArgsConstructor
@Component
public class EventPublisherImpl implements EventPublisher {

    private final ObjectMapper objectMapper;
    private final DomainOutboxDaoInterface domainOutboxDao;

    @Transactional
    @Override
    public void publish(List<? extends DomainEvent> events) {
        if (events == null || events.isEmpty()) return;
  
        try {
            for (DomainEvent e : events) {
                if(e instanceof CouponIsNotPublic) {
                    publishCouponNotPublic((CouponIsNotPublic) e);

                }
                if(e instanceof CouponPublished) {
                    publishCouponPublished((CouponPublished) e);
                }
            }
        } catch (Exception e) {
            log.error("Serializer error", e.getMessage());
            throw new RuntimeException("Serializer error:" + e.getMessage());
        }
    }

    public void publishCouponNotPublic(CouponIsNotPublic event) throws JsonProcessingException {
        String payload = objectMapper.writeValueAsString(event);
        domainOutboxDao.save(event.domain(), payload);
    }

    public void publishCouponPublished(CouponPublished event) throws JsonProcessingException{
        String payload = objectMapper.writeValueAsString(event);
        domainOutboxDao.save(event.domain(), payload);
    }
}
