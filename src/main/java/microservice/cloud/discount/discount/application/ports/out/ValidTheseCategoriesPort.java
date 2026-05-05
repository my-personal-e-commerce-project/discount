package microservice.cloud.discount.discount.application.ports.out;

import java.util.Set;

public interface ValidTheseCategoriesPort {

    public void execute(Set<String> categories);    
}
