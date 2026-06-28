package microservice.cloud.discount.discount.infrastrcture.persistence.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import microservice.cloud.discount.discount.domain.entity.Discount;

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
    private boolean autoApply;
    private boolean isActive;
    private LocalDateTime expiredAt;

    @Version
    private Long version;

    public void updateFromDomain(Discount discount) {
        this.name = discount.name();
        this.slug = discount.slug().value();
        this.discountType = discount.discountType().toString();
        this.percentageValue = discount.percentageValue() == null? null: discount.percentageValue().value();
        this.decrementValue = discount.decrementValue() == null? null: discount.decrementValue().value();
        this.allowedCategories = discount.allowedCategories() == null
                ? null
                : discount.allowedCategories()
                    .stream()
                    .map((String c) -> new DiscountEntity.DiscountCategoryReference(c))
                    .collect(Collectors.toSet());
        this.globalCategories = discount.globalCategories();
        this.minPrice = discount.minPrice() == null? null: discount.minPrice().value();
        this.maxPrice = discount.maxPrice() == null? null: discount.maxPrice().value();
        this.minStock = discount.minStock() == null? null: discount.minStock().value();
        this.maxStock = discount.maxStock() == null? null: discount.maxStock().value();
        this.autoApply = discount.autoApply();
        this.isActive = discount.isActive();
        this.expiredAt = discount.expiredAt();
    } 
}
