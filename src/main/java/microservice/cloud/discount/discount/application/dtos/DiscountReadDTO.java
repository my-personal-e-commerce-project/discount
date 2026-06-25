package microservice.cloud.discount.discount.application.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record DiscountReadDTO(
    String id,
    String name,
    String slug,
    String discountType,
    Double percentageValue,
    Double decrementValue,
    List<String> allowedCategories,
    boolean isGlobalCategories,
    Double minPrice,
    Double maxPrice,
    Integer minStock,
    Integer maxStock,
    boolean isActive,
    LocalDateTime expiredAt
) {}
