package microservice.cloud.discount.discount.infrastrcture.persistence.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import microservice.cloud.discount.discount.application.dtos.DiscountReadDTO;

public class DiscountResultSetExtractor implements ResultSetExtractor<List<DiscountReadDTO>> {

    @Override
    public List<DiscountReadDTO> extractData(ResultSet rs) 
        throws SQLException, DataAccessException 
    {
        Map<String, DiscountReadDTO> discounts = new LinkedHashMap<>();

        String rawCategories = null;
        String disId = null;

        while (rs.next()) {
            disId = rs.getString("disId");

            DiscountReadDTO discount = discounts.get(disId);
            if (discount == null) {
                discount = new DiscountReadDTO(
                    disId,
                    rs.getString("name"),
                    rs.getString("slug"),
                    rs.getString("discountType"),
                    rs.getDouble("percentageValue"),
                    rs.getDouble("decrementValue"),
                    new ArrayList<>(),
                    rs.getBoolean("isGlobalCategories"),
                    rs.getDouble("minPrice"),
                    rs.getDouble("maxPrice"),
                    rs.getInt("minStock"),
                    rs.getInt("maxStock"),
                    rs.getBoolean("isActive"),
                    null
                );
            }

            rawCategories = rs.getString("catId");
            if (rawCategories != null && !rawCategories.isBlank()) {
                discount.allowedCategories().add(rs.getString("catId"));
            }

            discounts.put(discount.id(), discount);
        }

        return new ArrayList<>(discounts.values());
    }
}
