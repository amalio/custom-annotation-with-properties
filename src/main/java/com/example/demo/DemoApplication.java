package com.example.demo;

import feign.Contract;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@SpringBootApplication
@Slf4j
@EnableFeignClients("com.example.demo")
@EnableConfigurationProperties({CustomProperties.class})
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
    ApplicationRunner applicationRunner(MyFeignService myFeignService, Environment environment, CustomProperties properties) {
        return (args) -> {
            List<SimplePOJO> list = myFeignService.simpleGet();
            list.forEach(pojo -> {

                log.info("id: " + pojo.getId());

                MyAnnotation myAnnotation = AnnotationUtils.findAnnotation(pojo.getClass(), MyAnnotation.class);

                log.info("value in application.yml is: {}", myAnnotation.myProperty());
                log.info("value in application.yml is: {}", environment.getProperty(myAnnotation.myProperty()));
                try {
                    log.info("value in application.yml is: {}", PropertyUtils.getProperty(properties, "prop"));
                } catch (Exception e) {
                   log.error("error: {}", e);
                }
            });


        };
    }

    @Bean
    public Contract contract() {
        return new Contract.Default();
    }

    @Bean
    public Retryer feignRetryer() {
        return Retryer.NEVER_RETRY;
    }

    /*@Bean
    public PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
        c.setLocation(new ClassPathResource("application.yml"));
        return c;
    }*/


    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
