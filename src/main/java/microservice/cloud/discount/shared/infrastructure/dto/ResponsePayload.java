package microservice.cloud.discount.shared.infrastructure.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponsePayload <T> {

    @Builder.Default
    private String message = "success";
    private T payload;
    private Map<String, String> errors;
}
