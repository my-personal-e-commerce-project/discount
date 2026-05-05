package microservice.cloud.discount.shared.application.ports.out;

import microservice.cloud.discount.shared.domain.value_objects.Me;

public interface GetMePort {

    public Me execute();
}
