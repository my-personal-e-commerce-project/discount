package microservice.cloud.discount.shared.application.ports.out;

public interface DomainOutboxDaoInterface {

    void save(
        String topic,
        String payload
    );
}
