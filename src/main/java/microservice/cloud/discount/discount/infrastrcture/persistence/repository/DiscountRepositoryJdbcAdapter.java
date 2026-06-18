package microservice.cloud.discount.discount.infrastrcture.persistence.repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import microservice.cloud.discount.discount.domain.entity.Discount;
import microservice.cloud.discount.discount.domain.exception.ThisDiscountAlreadyExistsException;
import microservice.cloud.discount.discount.domain.repository.DiscountRepository;
import microservice.cloud.discount.discount.domain.value_objects.DiscountType;
import microservice.cloud.discount.discount.domain.value_objects.Percentage;
import microservice.cloud.discount.discount.infrastrcture.persistence.model.DiscountEntity;
import microservice.cloud.discount.discount.domain.value_objects.Price;
import microservice.cloud.discount.discount.domain.value_objects.Quantity;
import microservice.cloud.discount.shared.domain.exception.DataNotFound;
import microservice.cloud.discount.shared.domain.value_objects.Id;

@RequiredArgsConstructor
@Repository
public class DiscountRepositoryJdbcAdapter implements DiscountRepository {

    private final JdbcAggregateTemplate jdbcAggregateTemplate;
    private final DiscountJdbcRepository discountJdbcRepository;

    @Transactional
    @Override
    public void save(Discount discount) {
        jdbcAggregateTemplate.insert(toMap(discount));
    }

    @Transactional
    @Override
    public void update(Discount discount) {
        jdbcAggregateTemplate.update(toMap(discount));
    }

    @Transactional
    @Override
    public void delete(Discount discount) {
        jdbcAggregateTemplate.deleteById(discount.id().value(), DiscountEntity.class);
    }

    @Transactional
    @Override
    public void removeDiscountCategoriesLog(Id id) {
        discountJdbcRepository.deleteByCategoryId(id.value());
    }

    @Transactional(readOnly = true)
    @Override
    public Discount getById(Id id) {
        DiscountEntity entity = jdbcAggregateTemplate.findById(id.value(), DiscountEntity.class);

        if(entity == null)
            throw new DataNotFound("Discount not found");

        return toMap(entity);
    }

    @Override
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
    ) {
        List<DiscountEntity> discounts = discountJdbcRepository
            .findByNameContainingAndDiscountTypeAndGlobalCategoriesAndPercentageValueAndDecrementValueAndMinPriceAndMaxPriceAndMinStockAndMaxStock(
                name,
                discountType.toString(), 
                globalCategories,
                percentageValue == null? null: percentageValue.value(), 
                decrementValue == null? null: decrementValue.value(),
                minPrice == null? null: minPrice.value(), 
                maxPrice == null? null: maxPrice.value(), 
                minStock == null? null: minStock.value(), 
                maxStock == null? null: maxStock.value()
            );

        discounts.forEach(d -> {
            Set<String> categories = d.getAllowedCategories()
                .stream()
                .map(c -> c.categoryId())
                .collect(Collectors.toSet());

            if(
                categories.containsAll(allowedCategories)
                && allowedCategories.containsAll(categories)
                && d.getName().equals(name)
            ) {
                if(!discounts.isEmpty())
                    throw new ThisDiscountAlreadyExistsException();
            }
        });
    }

    @Override
    public List<Discount> getDiscountsByIds(Set<String> discountIds) {
        List<DiscountEntity> discounts = 
            discountJdbcRepository.findByIdIn(discountIds);

        return discounts.stream().map(this::toMap).toList();
    }

    private Discount toMap(DiscountEntity entity) {
    
        return new Discount(
            Id.fromString(entity.getId()), 
            entity.getName(), 
            DiscountType.valueOf(entity.getDiscountType()), 
            entity.getPercentageValue() == null? null: new Percentage(entity.getPercentageValue()), 
            entity.getDecrementValue() == null? null: new Price(entity.getDecrementValue()), 
            entity.getAllowedCategories() == null
                ? null
                : entity.getAllowedCategories()
                    .stream()
                    .map(c -> c.categoryId())
                    .collect(Collectors.toSet()), 
            entity.isGlobalCategories(), 
            entity.getMinPrice() == null? null: new Price(entity.getMinPrice()), 
            entity.getMaxPrice() == null? null: new Price(entity.getMaxPrice()), 
            entity.getMinStock() == null? null: new Quantity(entity.getMinStock()),
            entity.getMaxStock() == null? null: new Quantity(entity.getMinStock()),
            entity.getExpiredAt()
        );
    }

    private DiscountEntity toMap(Discount discount) {
    
        return new DiscountEntity(
            discount.id().value(), 
            discount.name(), 
            discount.discountType().toString(), 
            discount.percentageValue() == null? null: discount.percentageValue().value(), 
            discount.decrementValue() == null? null: discount.decrementValue().value(),
            discount.allowedCategories() == null
                ? null
                : discount.allowedCategories()
                    .stream()
                    .map((String c) -> new DiscountEntity.DiscountCategoryReference(c))
                    .collect(Collectors.toSet()), 
            discount.globalCategories(), 
            discount.minPrice() == null? null: discount.minPrice().value(), 
            discount.maxPrice() == null? null: discount.maxPrice().value(), 
            discount.minStock() == null? null: discount.minStock().value(), 
            discount.maxStock() == null? null: discount.maxStock().value(), 
            discount.expiredAt()
        );
    }
}
