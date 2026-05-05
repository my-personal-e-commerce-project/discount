package microservice.cloud.discount.discount.infrastrcture.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import microservice.cloud.discount.discount.application.ports.out.DiscountReadRepository;
import microservice.cloud.discount.discount.application.ports.out.ValidTheseCategoriesPort;
import microservice.cloud.discount.discount.application.use_cases.CreateDiscountUseCase;
import microservice.cloud.discount.discount.application.use_cases.DeleteDiscountUseCase;
import microservice.cloud.discount.discount.application.use_cases.ListDiscountsUseCase;
import microservice.cloud.discount.discount.application.use_cases.RemoveDiscountCategoriesLogUseCase;
import microservice.cloud.discount.discount.application.use_cases.UpdateDiscountUseCase;
import microservice.cloud.discount.discount.domain.repository.DiscountRepository;
import microservice.cloud.discount.shared.application.ports.out.GetMePort;

@Configuration
public class DiscountConfigAdapter {
   
    @Bean
    public CreateDiscountUseCase createCouponUseCase(
        DiscountRepository discountRepository,
        ValidTheseCategoriesPort validTheseCategoriesPort,
        GetMePort getMePort
    ) {
        return new CreateDiscountUseCase(discountRepository, validTheseCategoriesPort, getMePort);
    }

    @Bean
    public UpdateDiscountUseCase updateDiscountUseCase(
        DiscountRepository discountRepository,
        ValidTheseCategoriesPort validTheseCategoriesPort,
        GetMePort getMePort
    ) {
    
        return new UpdateDiscountUseCase(discountRepository, validTheseCategoriesPort, getMePort);
    }

    @Bean
    public DeleteDiscountUseCase deleteDiscountUseCase(
        DiscountRepository discountRepository,
        GetMePort getMePort
    ) {
        return new DeleteDiscountUseCase(discountRepository, getMePort);
    }
    
    @Bean
    public RemoveDiscountCategoriesLogUseCase removeDiscountCategoriesLogUseCase(
        DiscountRepository discountRepository
    ) {
        return new RemoveDiscountCategoriesLogUseCase(discountRepository);
    }

    @Bean 
    public ListDiscountsUseCase listCouponsUseCase(
        DiscountReadRepository discountReadRepository
    ) {
        return new ListDiscountsUseCase(discountReadRepository);
    }
}
