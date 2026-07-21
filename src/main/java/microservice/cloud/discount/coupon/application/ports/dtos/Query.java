package microservice.cloud.discount.coupon.application.ports.dtos;

import java.util.Set;

public record Query(
    int page,
    int size,
    Set<String> CODES
) {}
