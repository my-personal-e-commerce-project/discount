package microservice.cloud.discount.coupon.application.ports.dtos;

public record CouponReadDTO(
    String id,
    String discountId,
    String code,
    String visibility,
    Integer sales,
    Integer maxSales,
    String expiredAt
) {}
