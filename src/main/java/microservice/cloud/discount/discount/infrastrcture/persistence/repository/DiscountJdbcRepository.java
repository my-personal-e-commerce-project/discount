package microservice.cloud.discount.discount.infrastrcture.persistence.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import feign.Param;
import microservice.cloud.discount.discount.infrastrcture.persistence.model.DiscountEntity;

@Repository
public interface DiscountJdbcRepository extends PagingAndSortingRepository<DiscountEntity, String> {

    List<DiscountEntity> findByIdIn(Set<String> ids);

    @Modifying
    @Query("DELETE FROM discount_categories WHERE category_id = :categoryId")
    void deleteByCategoryId(@Param("categoryId") String categoryId);
    
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
