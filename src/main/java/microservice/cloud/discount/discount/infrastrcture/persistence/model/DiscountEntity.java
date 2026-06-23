package microservice.cloud.discount.discount.infrastrcture.persistence.model;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Table("discounts")
@Getter
@AllArgsConstructor
public class DiscountEntity {

    @Id
    private final String id;
    private String name;
    private String slug;
    private String discountType;
    private Double percentageValue;
    private Double decrementValue;
   
    @Table("discount_categories")
    public static record DiscountCategoryReference(
        @Column("category_id") String categoryId
    ) {}

    @MappedCollection(idColumn = "discount_id")
    private Set<DiscountCategoryReference> allowedCategories;
    
    private boolean globalCategories;
    private Double minPrice;
    private Double maxPrice;
    private Integer minStock;
    private Integer maxStock;
    private boolean isActive;
    private LocalDateTime expiredAt;
}
