package microservice.cloud.discount.discount.application.use_cases;

import microservice.cloud.discount.discount.application.ports.out.ValidTheseCategoriesPort;
import microservice.cloud.discount.discount.domain.entity.Discount;
import microservice.cloud.discount.discount.domain.repository.DiscountRepository;
import microservice.cloud.discount.shared.application.ports.out.GetMePort;
import microservice.cloud.discount.shared.domain.value_objects.Me;
import microservice.cloud.discount.shared.domain.value_objects.Permission;

public class UpdateDiscountUseCase {

    private final DiscountRepository discountRepository;
    private final ValidTheseCategoriesPort validTheseCategoriesPort;
    private final GetMePort getMePort;

    public UpdateDiscountUseCase(
        DiscountRepository discountRepository,
        ValidTheseCategoriesPort validTheseCategoriesPort,
        GetMePort getMePort
    ) {
        this.discountRepository = discountRepository;
        this.validTheseCategoriesPort = validTheseCategoriesPort;
        this.getMePort = getMePort;
    }

    public void execute(Discount discount) {
        Me me = getMePort.execute();

        if(me == null)
            throw new RuntimeException("You do not have permission to perform this action");

        me.IHavePermission(Permission.createDiscount());

        if(!discount.globalCategories()) validTheseCategoriesPort.execute(discount.allowedCategories());

        discountRepository.getById(discount.id());

        discountRepository.update(discount);
    }
}
