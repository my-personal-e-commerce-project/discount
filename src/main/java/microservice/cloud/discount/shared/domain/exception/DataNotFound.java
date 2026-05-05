package microservice.cloud.discount.shared.domain.exception;

public class DataNotFound extends RuntimeException {

    public DataNotFound(String msg) {
        super(msg);
    }
}
