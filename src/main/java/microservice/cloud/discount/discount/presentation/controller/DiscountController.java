package microservice.cloud.discount.discount.presentation.controller;

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
import microservice.cloud.discount.discount.application.use_cases.CreateDiscountUseCase;
import microservice.cloud.discount.discount.application.use_cases.DeleteDiscountUseCase;
import microservice.cloud.discount.discount.application.use_cases.ListDiscountsUseCase;
import microservice.cloud.discount.discount.application.use_cases.UpdateDiscountUseCase;
import microservice.cloud.discount.discount.domain.entity.Discount;
import microservice.cloud.discount.discount.domain.value_objects.DiscountType;
import microservice.cloud.discount.discount.domain.value_objects.Percentage;
import microservice.cloud.discount.discount.presentation.validate.DiscountDTO;
import microservice.cloud.discount.discount.domain.value_objects.Price;
import microservice.cloud.discount.discount.domain.value_objects.Quantity;
import microservice.cloud.discount.shared.application.dto.Pagination;
import microservice.cloud.discount.shared.domain.value_objects.Id;

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
        @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
            listDiscountsUseCase.execute(page, size)
        );
    }

    @PostMapping
    public ResponseEntity<DiscountDTO> createDiscount(
        @RequestBody @Valid DiscountDTO discount
    ) {
        discount.setId(Id.generate().value());

        createDiscountUseCase.execute(
            Id.fromString(discount.getId()),
            discount.getName(),
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
            discount.isAutoApply(),
            discount.getExpiredAt()
        );

        return ResponseEntity.ok(discount);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiscountDTO> updateDiscount(
        @RequestBody @Valid DiscountDTO discount,
        @PathVariable String id
    ) {
        discount.setId(Id.generate().value());

        updateDiscountUseCase.execute(
            new Discount(
                Id.fromString(id),
                discount.getName(),
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
                discount.isAutoApply(),
                discount.getExpiredAt()
            )
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
