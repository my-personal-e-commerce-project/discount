package microservice.cloud.discount.discount.infrastrcture.persistence.repository;

import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import microservice.cloud.discount.discount.application.dtos.DiscountReadDTO;
import microservice.cloud.discount.discount.application.ports.out.DiscountReadRepository;
import microservice.cloud.discount.discount.infrastrcture.persistence.extractor.DiscountResultSetExtractor;
import microservice.cloud.discount.shared.application.dto.Pagination;

@RequiredArgsConstructor
@Repository
public class DiscountReadRepositoryJdbcAdapter implements DiscountReadRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Transactional(readOnly = true)
    @Override
    public Pagination<DiscountReadDTO> listDiscounts(
        int page, 
        int size,
        List<String> allowedCategories,
        Boolean globalCategories,
        Boolean isActive,
        Integer minStock,
        Integer maxStock,
        Double minPrice,
        Double maxPrice
    ) {
        int offset = page * size;
        
        StringBuilder sql = new StringBuilder("""
            SELECT d.id AS disId, d.name AS name, d.slug AS slug, 
                   d.discount_type AS discountType, d.percentage_value AS percentageValue,
                   d.decrement_value AS decrementValue, d.global_categories AS isGlobalCategories,
                   d.min_price AS minPrice, d.max_price AS maxPrice,
                   d.min_stock AS minStock, d.max_stock AS maxStock,
                   d.is_active AS isActive, d.expired_at AS expiredAt,
                   dc.category_id AS catId
            FROM discounts d 
            LEFT JOIN discount_categories dc ON d.id = dc.discount_id
            WHERE 1=1
            """);

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("limit", size, Types.INTEGER);
        params.addValue("offset", offset, Types.INTEGER);

        if (globalCategories != null) {
            sql.append(" AND d.global_categories = :globalCategories");
            params.addValue("globalCategories", globalCategories, Types.BOOLEAN);
        }
        if (isActive != null) {
            sql.append(" AND d.is_active = :isActive");
            params.addValue("isActive", isActive, Types.BOOLEAN);
        }
        if (minPrice != null) {
            sql.append(" AND d.min_price <= :minPrice");
            params.addValue("minPrice", minPrice, Types.DOUBLE);
        }
        if (maxPrice != null) {
            sql.append(" AND d.max_price >= :maxPrice");
            params.addValue("maxPrice", maxPrice, Types.DOUBLE);
        }
        if (minStock != null) {
            sql.append(" AND d.min_stock <= :minStock");
            params.addValue("minStock", minStock, Types.INTEGER);
        }
        
        if (maxStock != null) {
            sql.append(" AND d.max_stock >= :maxStock");
            params.addValue("maxStock", maxStock, Types.INTEGER);
        }

        sql.append(" LIMIT :limit OFFSET :offset");

        List<DiscountReadDTO> discounts = namedParameterJdbcTemplate.query(
            sql.toString(), 
            params,
            new DiscountResultSetExtractor()
        );

        long total = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM discounts;", Long.class);

        int totalPages = (size == 0) ? 1 : (int) Math.ceil((double) total / size);
    
        int last_page = Math.max(0, totalPages - 1);

        return new Pagination<DiscountReadDTO>(
            discounts,
            last_page, 
            page
        );
        
    }
}
