package com.daoki.basic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * @author Alan
 * Date: Created in 2019-03-12 11:29
 * Utils: Intellij Idea
 * Description: 启动类
 */
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class}
,scanBasePackages = {"com.daoki"})
@ComponentScan("com.daoki")
//@MapperScan("com.daoki.basic.mapper")
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}
