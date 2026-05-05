package microservice.cloud.discount.shared.application.dto;

import java.util.List;

public record Pagination<T> (
    List<T> results,
    int last_page,
    int current_page
) {}
