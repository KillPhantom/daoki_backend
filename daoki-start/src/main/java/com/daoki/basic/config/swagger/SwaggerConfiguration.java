package com.daoki.basic.config.swagger;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.StringVendorExtension;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Alan
 * Date: Created in 18/8/29 上午9:54
 * Utils: Intellij Idea
 * Description: swagger配置类
 */
@Configuration
@EnableSwagger2
@AllArgsConstructor
@Profile({"dev", "test"})
@EnableConfigurationProperties(SwaggerInfo.class)
public class SwaggerConfiguration {

    private SwaggerInfo swaggerInfo;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(swaggerInfo.getGroupName())
                .useDefaultResponseMessages(false)
                .enableUrlTemplating(false)
                .forCodeGeneration(true)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerInfo.getBasePackage()))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 设置api信息
     *
     * @return 返回ApiInfo
     */
    private ApiInfo apiInfo() {
        StringVendorExtension vendorExtension = new StringVendorExtension("", "");
        Collection<VendorExtension> vendorExtensions = new ArrayList<>();
        vendorExtensions.add(vendorExtension);
        Contact contact = new Contact("", "", "");
        return new ApiInfo(
                swaggerInfo.getTitle(),
                swaggerInfo.getDescription(),
                swaggerInfo.getVersion(),
                "", contact, "", "",
                vendorExtensions);
    }

}
