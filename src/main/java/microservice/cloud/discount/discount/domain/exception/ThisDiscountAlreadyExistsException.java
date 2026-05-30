package microservice.cloud.discount.discount.domain.exception;

public class ThisDiscountAlreadyExistsException extends RuntimeException{

    public ThisDiscountAlreadyExistsException() {
        super("Already exists a discount with followings attributes: name, globalCategories, discount type, percentage value, decrement value, min price, max price, min stock and max stock");
    }

    public ThisDiscountAlreadyExistsException(String msg) {
        super(msg);
    }
}
