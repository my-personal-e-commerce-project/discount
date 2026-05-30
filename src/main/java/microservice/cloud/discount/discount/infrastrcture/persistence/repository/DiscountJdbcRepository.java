package microservice.cloud.discount.discount.infrastrcture.persistence.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import microservice.cloud.discount.discount.infrastrcture.persistence.model.DiscountEntity;

@Repository
public interface DiscountJdbcRepository extends PagingAndSortingRepository<DiscountEntity, String> {

    List<DiscountEntity> findByIdIn(Set<String> ids);

    List<DiscountEntity> findByNameContainingAndDiscountTypeAndGlobalCategoriesAndPercentageValueAndDecrementValueAndMinPriceAndMaxPriceAndMinStockAndMaxStock(
        String name, 
        String discountType,
        boolean globalCategories,
        Double percentageValue,
        Double decrementValue,
        Double minPrice,
        Double maxPrice,
        Integer minStock,
        Integer maxStock
    );

    long countByIdIn(Set<String> ids);
}
