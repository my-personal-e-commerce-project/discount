package microservice.cloud.discount.discount.presentation.controller;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import microservice.cloud.discount.discount.application.dtos.DiscountReadDTO;
import microservice.cloud.discount.discount.application.dtos.Query;
import microservice.cloud.discount.discount.application.use_cases.CreateDiscountUseCase;
import microservice.cloud.discount.discount.application.use_cases.DeleteDiscountUseCase;
import microservice.cloud.discount.discount.application.use_cases.ListDiscountsUseCase;
import microservice.cloud.discount.discount.application.use_cases.UpdateDiscountUseCase;
import microservice.cloud.discount.discount.domain.value_objects.DiscountType;
import microservice.cloud.discount.discount.domain.value_objects.Percentage;
import microservice.cloud.discount.discount.presentation.validate.DiscountDTO;
import microservice.cloud.discount.discount.presentation.validate.UpdateDiscountDTO;
import microservice.cloud.discount.discount.domain.value_objects.Price;
import microservice.cloud.discount.discount.domain.value_objects.Quantity;
import microservice.cloud.discount.shared.application.dto.Pagination;
import microservice.cloud.discount.shared.domain.value_objects.Id;
import microservice.cloud.discount.shared.domain.value_objects.Slug;

@RequiredArgsConstructor
@RequestMapping("/api/v1/discounts")
@RestController
public class DiscountController {

    private final CreateDiscountUseCase createDiscountUseCase;
    private final UpdateDiscountUseCase updateDiscountUseCase;
    private final DeleteDiscountUseCase deleteDiscountUseCase;
    private final ListDiscountsUseCase listDiscountsUseCase;

    @GetMapping 
    public ResponseEntity<Pagination<DiscountReadDTO>> listDiscounts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) String query,
        @RequestParam(required = false) Set<String> allowedCategories,
        @RequestParam(required = false) Boolean globalCategories,
        @RequestParam(required = false) Boolean isActive,
        @RequestParam(required = false) Integer minStock,
        @RequestParam(required = false) Integer maxStock,
        @RequestParam(required = false) Double minPrice,
        @RequestParam(required = false) Double maxPrice
    ) {
        return ResponseEntity.ok(
            listDiscountsUseCase.execute(
                page, 
                size, 
                new Query(
                    query,
                    allowedCategories == null
                        ? null
                        : new ArrayList<>(allowedCategories),
                    globalCategories,
                    isActive,
                    minStock,
                    maxStock,
                    minPrice,
                    maxPrice
                )
            )
        );
    }

    @PostMapping
    public ResponseEntity<DiscountDTO> createDiscount(
        @RequestBody @Valid DiscountDTO discount
    ) {
        discount.setId(Id.generate().value());
        discount.setSlug(Slug.create(discount.getSlug()).value());

        createDiscountUseCase.execute(
            Id.fromString(discount.getId()),
            discount.getName(),
            Slug.fromString(discount.getSlug()),
            DiscountType.valueOf(discount.getDiscountType()),
            discount.getPercentageValue() == null
                ? null
                : new Percentage(discount.getPercentageValue()),
            discount.getDecrementValue() == null? null: new Price(discount.getDecrementValue()),
            discount.getAllowedCategories(),
            discount.isGlobalCategories(),
            discount.getMinPrice() == null
                ? null
                : new Price(discount.getMinPrice()),
            discount.getMaxPrice() == null
                ? null
                : new Price(discount.getMaxPrice()),
            discount.getMinStock() == null? null: new Quantity(discount.getMinStock()),
            discount.getMaxStock() == null? null: new Quantity(discount.getMaxStock()),
            discount.isActive(),
            discount.getExpiredAt()
        );

        return ResponseEntity.ok(discount);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateDiscountDTO> updateDiscount(
        @RequestBody @Valid UpdateDiscountDTO discount,
        @PathVariable String id
    ) {
        discount.setId(id);
        discount.setSlug(Slug.create(discount.getSlug()).value());
        
        updateDiscountUseCase.execute(
            Id.fromString(id),
            discount.getName(),
            Slug.fromString(discount.getSlug()),
            DiscountType.valueOf(discount.getDiscountType()),
            discount.getPercentageValue() == null
                ? null
                : new Percentage(discount.getPercentageValue()),
            discount.getDecrementValue() == null? null: new Price(discount.getDecrementValue()),
            discount.getAllowedCategories(),
            discount.isGlobalCategories(),
            discount.getMinPrice() == null
                ? null
                : new Price(discount.getMinPrice()),
            discount.getMaxPrice() == null
                ? null
                : new Price(discount.getMaxPrice()),
            discount.getMinStock() == null? null: new Quantity(discount.getMinStock()),
            discount.getMaxStock() == null? null: new Quantity(discount.getMaxStock()),
            discount.isActive(),
            discount.getExpiredAt()
        );

        return ResponseEntity.ok(discount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDiscount(
        @PathVariable String id
    ) {
        deleteDiscountUseCase.execute(Id.fromString(id));

        return ResponseEntity.noContent().build();
    }
}
