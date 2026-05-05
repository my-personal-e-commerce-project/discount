package microservice.cloud.discount.discount.application.use_cases;

import java.time.LocalDateTime;
import java.util.Set;

import microservice.cloud.discount.discount.application.ports.out.ValidTheseCategoriesPort;
import microservice.cloud.discount.discount.domain.entity.Discount;
import microservice.cloud.discount.discount.domain.repository.DiscountRepository;
import microservice.cloud.discount.discount.domain.value_objects.DiscountType;
import microservice.cloud.discount.discount.domain.value_objects.Percentage;
import microservice.cloud.discount.discount.domain.value_objects.Price;
import microservice.cloud.discount.discount.domain.value_objects.Quantity;
import microservice.cloud.discount.shared.application.ports.out.GetMePort;
import microservice.cloud.discount.shared.domain.value_objects.Id;
import microservice.cloud.discount.shared.domain.value_objects.Me;
import microservice.cloud.discount.shared.domain.value_objects.Permission;

public class CreateDiscountUseCase {
    private final DiscountRepository discountRepository;
    private final ValidTheseCategoriesPort validTheseCategoriesPort;
    private final GetMePort getMePort;

    public CreateDiscountUseCase (
        DiscountRepository discountRepository,
        ValidTheseCategoriesPort validTheseCategoriesPort,
        GetMePort getMePort
    ) {
        this.discountRepository = discountRepository;
        this.validTheseCategoriesPort = validTheseCategoriesPort;
        this.getMePort = getMePort;
    }

    public void execute(
        Id id,
        String name, 
        DiscountType discountType,
        Percentage percentageValue,
        Price decrementValue,
        Set<String> allowedCategories,
        boolean globalCategories,
        Price minPrice,
        Price maxPrice,
        Quantity minStock,
        Quantity maxStock,
        boolean autoApply,
        LocalDateTime expiredAt
    ) {
        Me me = getMePort.execute();

        if(me == null)
            throw new RuntimeException("You do not have permission to perform this action");

        me.IHavePermission(Permission.createDiscount());

        if(!globalCategories) validTheseCategoriesPort.execute(allowedCategories);

        Discount discount = Discount.factory(
            id,
            name,
            discountType,
            percentageValue,
            decrementValue,
            allowedCategories,
            globalCategories,
            minPrice,
            maxPrice,
            minStock,
            maxStock,
            autoApply,
            expiredAt
        );

        discountRepository.save(discount);
    }
}
