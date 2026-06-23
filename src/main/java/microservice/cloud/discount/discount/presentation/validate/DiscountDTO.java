package microservice.cloud.discount.discount.presentation.validate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class DiscountDTO {

    private String id;
    @NotEmpty
    private String name;
   
    @NotEmpty
    private String slug;

    @NotEmpty
    private String discountType;
   
    private Double percentageValue;
    private Double decrementValue;

    private Set<String> allowedCategories = new HashSet<>();
    
    private boolean globalCategories = false;
    private Double minPrice = null;
    private Double maxPrice = null;
    private Integer minStock = null;
    private Integer maxStock = null;
    private boolean isActive = false;

    @NotNull
    @Future
    private LocalDateTime expiredAt;
}
