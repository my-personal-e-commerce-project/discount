package microservice.cloud.discount.discount.application.dtos;

import java.util.List;

public record Query(
    String query,
    List<String> allowedCategories,
    Boolean globalCategories,
    Boolean isActive,
    Boolean autoApply,
    Integer minStock,
    Integer maxStock,
    Double minPrice,
    Double maxPrice
) {}
