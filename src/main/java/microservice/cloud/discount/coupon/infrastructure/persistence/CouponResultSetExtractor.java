package microservice.cloud.discount.coupon.infrastructure.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import microservice.cloud.discount.coupon.application.ports.dtos.CouponReadDTO;

public class CouponResultSetExtractor implements ResultSetExtractor<List<CouponReadDTO>>{


    @Override
    public List<CouponReadDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<String, CouponReadDTO> coupons = new LinkedHashMap<>();

        String coupId = null;

        while (rs.next()) {
            coupId = rs.getString("coupId");

            CouponReadDTO coupon = coupons.get(coupId);
            if (coupon == null) {
                coupon = new CouponReadDTO(
                    coupId,
                    rs.getString("discountId"),
                    rs.getString("code"),
                    rs.getString("visibility"),
                    rs.getInt("sales"),
                    rs.getInt("maxSales"),
                    rs.getString("expiredAt")
                );
            }

            coupons.put(coupon.id(), coupon);
        }

        return new ArrayList<>(coupons.values());
    }
}
