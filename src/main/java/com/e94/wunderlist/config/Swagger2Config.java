package com.e94.wunderlist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.e94.wunderlist"))
                .paths(PathSelectors.regex("/.*"))
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(apiEndPointsInfo());
    }


    private ApiInfo apiEndPointsInfo()
    {
        return new ApiInfoBuilder().title("WunderList Java Backend")
                .description("Overview")
                .contact(new Contact("Erick Canales",
                        "TBD",
                        "e94canales@gmail.com"))
                .license("MIT")
                .licenseUrl("TBD")
                .version("1.0.0")
                .build();
    }
}