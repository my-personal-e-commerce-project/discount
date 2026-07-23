package microservice.cloud.discount.coupon.infrastructure.persistence;

import java.util.List;
import java.sql.Types;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import microservice.cloud.discount.coupon.application.ports.dtos.CouponReadDTO;
import microservice.cloud.discount.coupon.application.ports.dtos.Query;
import microservice.cloud.discount.coupon.application.ports.out.CouponReadRepository;
import microservice.cloud.discount.shared.application.dto.Pagination;

@AllArgsConstructor
@Repository
public class CouponReadRepositoryJdbcAdapter implements CouponReadRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Pagination<CouponReadDTO> findAll(Query query) {
        int offset = query.page() * query.size();
        
        StringBuilder sql = new StringBuilder("""
            SELECT c.id AS coupId, c.code AS code, c.visibility AS visibility,
                   c.discount_id AS discountId,
                   cs.sales AS sales, c.max_sales AS maxSales,
                   c.expired_at AS expiredAt
            FROM coupons c
            LEFT JOIN coupons_sales cs ON cs.coupon_id = c.id
            WHERE 1=1
        """);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("limit", query.size(), Types.INTEGER);
        params.addValue("offset", offset, Types.INTEGER);

        if(query.CODES() == null || query.CODES().isEmpty()) {

            sql.append(" LIMIT :limit OFFSET :offset");
        } else {
            sql.append(" AND c.code IN (:codes)");
            params.addValue("codes", query.CODES());
        }

        List<CouponReadDTO> coupons = namedParameterJdbcTemplate.query(
            sql.toString(), 
            params,
            new CouponResultSetExtractor()
        );

        long total = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM discounts;", Long.class);

        int totalPages = (query.size() == 0) ? 1 : (int) Math.ceil((double) total / query.size());
    
        int last_page = Math.max(0, totalPages - 1);

        return new Pagination<CouponReadDTO>(
            coupons,
            last_page,
            query.page()
        );
    }
}
