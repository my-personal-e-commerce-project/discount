package microservice.cloud.discount.discount.domain.repository;

import java.util.List;
import java.util.Set;

import microservice.cloud.discount.discount.domain.entity.Discount;
import microservice.cloud.discount.discount.domain.value_objects.DiscountType;
import microservice.cloud.discount.discount.domain.value_objects.Percentage;
import microservice.cloud.discount.discount.domain.value_objects.Price;
import microservice.cloud.discount.discount.domain.value_objects.Quantity;
import microservice.cloud.discount.shared.domain.value_objects.Id;

public interface DiscountRepository {

    public void save(Discount discount);
    public void update(Discount discount);
    public void delete(Discount discount);
    public Discount getById(Id id);
    public void existsDiscountWithFollowingAttributes(
            String name,
            boolean globalCategories,
            Set<String> allowedCategories,
            DiscountType discountType,
            Percentage percentageValue,
            Price decrementValue,
            Price minPrice, 
            Price maxPrice, 
            Quantity minStock, 
            Quantity maxStock
        );
    public void removeDiscountCategoriesLog(Id id);
    public List<Discount> getDiscountsByIds(Set<String> couponIds);
}
