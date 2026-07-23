package microservice.cloud.discount.coupon.infrastructure.presentation.controller;

import java.util.Set;

import org.springframework.http.HttpStatus;
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
import microservice.cloud.discount.coupon.application.ports.dtos.CouponReadDTO;
import microservice.cloud.discount.coupon.application.ports.dtos.Query;
import microservice.cloud.discount.coupon.application.use_cases.CreateCouponUseCase;
import microservice.cloud.discount.coupon.application.use_cases.DeleteCouponUseCase;
import microservice.cloud.discount.coupon.application.use_cases.ListCouponsUseCase;
import microservice.cloud.discount.coupon.application.use_cases.UpdateCouponUseCase;
import microservice.cloud.discount.coupon.domain.value_objects.CouponCode;
import microservice.cloud.discount.coupon.domain.value_objects.CouponVisibility;
import microservice.cloud.discount.coupon.infrastructure.presentation.dtos.CouponDTO;
import microservice.cloud.discount.shared.application.dto.Pagination;
import microservice.cloud.discount.shared.domain.value_objects.Id;
import microservice.cloud.discount.shared.infrastructure.dto.ResponsePayload;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/coupons")
public class CouponController {

    private final ListCouponsUseCase listCouponsUseCase;
    private final CreateCouponUseCase createCouponUseCase;
    private final UpdateCouponUseCase updateCouponUseCase;
    private final DeleteCouponUseCase deleteCouponUseCase;

    @GetMapping
    public ResponseEntity<ResponsePayload<Pagination<CouponReadDTO>>> listCoupons(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) Set<String> CODES
    ) {
        Pagination<CouponReadDTO> result = null;

        result = listCouponsUseCase.execute(new Query(page, size, CODES));

        return new ResponseEntity<>(
            ResponsePayload.<Pagination<CouponReadDTO>>builder()
            .payload(
                result
            ).build(),
            HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<ResponsePayload<CouponDTO>> createCoupon(
        @RequestBody @Valid CouponDTO coupon
    ) {
        coupon.setId(Id.generate().value());

        Id couponSalesId = Id.generate();

        createCouponUseCase.execute(
            Id.fromString(coupon.getId()),
            Id.fromString(coupon.getDiscountId()),
            couponSalesId,
            new CouponCode(coupon.getCode()),
            coupon.getMaxSales(),
            CouponVisibility.valueOf(coupon.getVisibility()),
            coupon.getExpiredAt()
        );

        return new ResponseEntity<>(
            ResponsePayload.<CouponDTO>builder().payload(coupon).build(),
            HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePayload<CouponDTO>> updateCoupon(
        @RequestBody @Valid CouponDTO coupon, @PathVariable String id
    ) {
        coupon.setId(id);

        updateCouponUseCase.execute(
            Id.fromString(coupon.getId()),
            Id.fromString(coupon.getDiscountId()),
            new CouponCode(coupon.getCode()),
            coupon.getMaxSales(),
            CouponVisibility.valueOf(coupon.getVisibility()),
            coupon.getExpiredAt()
        );

        return new ResponseEntity<>(
            ResponsePayload.<CouponDTO>builder().payload(coupon).build(),
            HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponsePayload<CouponDTO>> deleteCoupon(
        @PathVariable String id
    ) {
        deleteCouponUseCase.execute(Id.fromString(id));

        return ResponseEntity.noContent().build();
    }
}
