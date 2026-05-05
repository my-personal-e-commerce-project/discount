package microservice.cloud.discount.shared.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class InventoryEventDto {
    private Payload payload;

    @Data
    public static class Payload {
        private After after;
        private String op;
    }

    @Data
    public static class After {
        @JsonProperty("aggregate_id")
        private String aggregateId;

        @JsonProperty("aggregate_type")
        private String aggregateType;

        @JsonProperty("created_at")
        private String createdAt;

        private String id; 

        private JsonNode payload; 

        private String type;
    }
}
