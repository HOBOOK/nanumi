package com.daou.config;


import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * SWAGGER 설정
 * @author pkh879
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private ApiInfo appInfo(){
        return new ApiInfoBuilder()
                .title("Nanumi")
                .description("다우 인턴 프로젝트 A조 팩스 번호 자원 관리 API 설명 문서입니다.")
                .version("v1")
                .build();
    }

    @Bean
    public Docket commonApi() {

        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder.name("Authorization")
                .description("Access Token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(true)
                .build();
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(parameterBuilder.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Nanumi")
                .apiInfo(this.appInfo())
                .forCodeGeneration(true)
                .genericModelSubstitutes(ResponseEntity.class)
                .ignoredParameterTypes(java.sql.Date.class)
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.newArrayList(apiKey()))
                .globalOperationParameters(parameters)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.daou.controller"))
                .paths(PathSelectors.ant("/api/**"))
                .build();
    }

    private ApiKey apiKey(){
        return new ApiKey("Authorization", "nanumiKey","header");
    }
    private SecurityContext securityContext(){
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("JWT", authorizationScopes));
    }

}
