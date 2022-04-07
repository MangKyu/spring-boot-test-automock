package io.github.mangkyu.springboot.test.automock.config;

import io.github.mangkyu.springboot.test.automock.beanfactorypostprocessor.AutoMockBeanFactoryPostProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
@ConditionalOnMissingBean(name = AutoMockAutoConfiguration.AUTO_MOCK_BEAN_NAME, search = SearchStrategy.CURRENT)
@ConditionalOnProperty(name = "io.github.mangkyu.automock.enable", havingValue = "true")
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class AutoMockAutoConfiguration {

    public static final String AUTO_MOCK_BEAN_NAME = "autoMockBeanFactoryPostProcessor";

    @Bean
    public AutoMockBeanFactoryPostProcessor autoMockBeanFactoryPostProcessor() {
        return new AutoMockBeanFactoryPostProcessor();
    }

}