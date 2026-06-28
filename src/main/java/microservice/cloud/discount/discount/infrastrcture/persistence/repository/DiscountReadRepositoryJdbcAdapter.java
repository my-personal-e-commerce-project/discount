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
import microservice.cloud.discount.discount.application.dtos.Query;
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
        Query query
    ) {
        int offset = page * size;
        
        StringBuilder sql = new StringBuilder("""
            SELECT d.id AS disId, d.name AS name, d.slug AS slug, 
                   d.discount_type AS discountType, d.percentage_value AS percentageValue,
                   d.decrement_value AS decrementValue, d.global_categories AS isGlobalCategories,
                   d.min_price AS minPrice, d.max_price AS maxPrice,
                   d.min_stock AS minStock, d.max_stock AS maxStock,
                   d.is_active AS isActive, d.expired_at AS expiredAt,
                   dc.category_id AS catId, d.auto_apply AS autoApply
            FROM discounts d 
            LEFT JOIN discount_categories dc ON d.id = dc.discount_id
            WHERE 1=1
            """);

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("limit", size, Types.INTEGER);
        params.addValue("offset", offset, Types.INTEGER);

        if (query.query() != null && !query.query().isBlank()) {
            sql.append(" AND d.name LIKE :query");
            params.addValue("query", query.query());
        }
        if (query.globalCategories() != null) {
            sql.append(" AND d.global_categories = :globalCategories");
            params.addValue("globalCategories", query.globalCategories(), Types.BOOLEAN);
        }
        if (query.autoApply() != null) {
            sql.append(" AND d.auto_apply = :autoApply");
            params.addValue("autoApply", query.autoApply(), Types.BOOLEAN);
        }
        if (query.isActive() != null) {
            sql.append(" AND d.is_active = :isActive");
            params.addValue("isActive", query.isActive(), Types.BOOLEAN);
        }
        if (query.minPrice() != null) {
            sql.append(" AND d.min_price <= :minPrice");
            params.addValue("minPrice", query.minPrice(), Types.DOUBLE);
        }
        if (query.maxPrice() != null) {
            sql.append(" AND d.max_price >= :maxPrice");
            params.addValue("maxPrice", query.maxPrice(), Types.DOUBLE);
        }
        if (query.minStock() != null) {
            sql.append(" AND d.min_stock <= :minStock");
            params.addValue("minStock", query.minStock(), Types.INTEGER);
        }
        if (query.maxStock() != null) {
            sql.append(" AND d.max_stock >= :maxStock");
            params.addValue("maxStock", query.maxStock(), Types.INTEGER);
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
