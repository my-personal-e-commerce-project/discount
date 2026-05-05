package microservice.cloud.discount.discount.domain.value_objects;

public record Quantity (
    int value
){
    public Quantity(int value) {
        if(value < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        this.value = value;
    }
}
