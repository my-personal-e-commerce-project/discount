package microservice.cloud.discount.shared.infrastructure.dto;

import java.time.LocalDateTime;

public record DeletedCategory(
    LocalDateTime occurredOn,
    String aggregateId
) {}
