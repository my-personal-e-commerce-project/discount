package microservice.cloud.discount.coupon.domain.value_objects;

public class CouponCode {

    private String code;

    public CouponCode(String code) {
        if (code == null) {
            throw new IllegalArgumentException("Coupon code cannot be null");
        }

        if (code.isBlank()) {
            throw new IllegalArgumentException("Coupon code cannot be empty");
        }

        this.code = code;
    }

    public String value() {
        return code;
    }
}
