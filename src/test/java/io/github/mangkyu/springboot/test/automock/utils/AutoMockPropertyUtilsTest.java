package io.github.mangkyu.springboot.test.automock.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AutoMockPropertyUtilsTest {

    @Autowired
    private ApplicationContext beanFactory;

    @Test
    void findBasePackages() {
        final String result = AutoMockPropertyUtils.findBasePackage(beanFactory);
        assertThat(result).isEqualTo("io.github.mangkyu.springboot.test.automock");
    }

}