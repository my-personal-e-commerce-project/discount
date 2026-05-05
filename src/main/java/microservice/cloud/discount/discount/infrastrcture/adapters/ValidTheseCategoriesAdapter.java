package microservice.cloud.discount.discount.infrastrcture.adapters;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import microservice.cloud.discount.discount.application.ports.out.ValidTheseCategoriesPort;
import microservice.cloud.discount.discount.infrastrcture.client.CategoryClient;
import microservice.cloud.discount.discount.infrastrcture.dto.Category;

@RequiredArgsConstructor
@Component
public class ValidTheseCategoriesAdapter implements ValidTheseCategoriesPort {

    private final CategoryClient categoryClient;

    @Override
    public void execute(Set<String> categoryIds) {
        ResponseEntity<List<Category>> result = categoryClient.find(categoryIds);
        
        List<Category> categories = result.getBody();

        List<String> foundIds = categories.stream()
            .map(Category::id)
            .toList();

        if (!foundIds.containsAll(categoryIds)) {
            throw new RuntimeException("Not all categories are valid.");
        }
    }
}
