package microservice.cloud.discount.discount.infrastrcture.client;

import java.util.List;
import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import microservice.cloud.discount.discount.infrastrcture.dto.Category;

@FeignClient(name = "inventory")
public interface CategoryClient {

    @GetMapping("/api/v1/categories")
    public ResponseEntity<List<Category>> find(@RequestParam("categoryIds") Set<String> categoryIds);
}
