package com.mhw.example.application;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by mahw on 2017/1/4.
 */
//@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan(basePackages = "com.mhw.example.configuration,com.mhw.example.controller,com.mhw.example.service")
@MapperScan(basePackages = "com.mhw.example.dao")
@EnableCaching
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
