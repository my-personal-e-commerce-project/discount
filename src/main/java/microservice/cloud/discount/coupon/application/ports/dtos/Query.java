package microservice.cloud.discount.coupon.application.ports.dtos;

public record Query(
    int page,
    int size
) {}
