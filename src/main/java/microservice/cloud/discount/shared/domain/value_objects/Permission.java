package microservice.cloud.discount.shared.domain.value_objects;

public record Permission (
    String value
) {
    public Permission(String value) {
        if(value == null)
            throw new IllegalArgumentException("The permission cannot be null");

        if(value.isBlank())
            throw new IllegalArgumentException("The permission cannot be empty");
    
        this.value = value;
    }

    public static Permission createDiscount() {
        return new Permission("create_discount");
    }

    public static Permission updateDiscount() {
        return new Permission("update_discount");
    }

    public static Permission deleteDiscount() {
        return new Permission("delete_discount");
    }

    public static Permission createCoupon() {
        return new Permission("create_coupon");
    }

    public static Permission updateCoupon() {
        return new Permission("update_coupon");
    }

    public static Permission deleteCoupon() {
        return new Permission("delete_coupon");
    }
}
