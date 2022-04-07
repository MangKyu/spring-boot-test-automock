package io.github.mangkyu.springboot.test.automock.utils;

import io.github.mangkyu.springboot.test.automock.testcontext.AutoMockMockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.MockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AutoMockBeanUtilsTest {

    @Autowired
    private ApplicationContext beanFactory;
    private Class<AutoMockMockService> clazz;
    private String beanName;

    @BeforeEach
    void init() {
        clazz = AutoMockMockService.class;
        beanName = "autoMockMockService";
    }

    @Test
    void findBasePackages() {
        final String result = AutoMockBeanUtils.findBasePackage(beanFactory);
        assertThat(result).isEqualTo("io.github.mangkyu.springboot.test.automock");
    }

    @Test
    void registerSingletonMockSuccess() {
        AutoMockBeanUtils.registerSingletonMock((ConfigurableListableBeanFactory) beanFactory, clazz, beanName);

        final AutoMockMockService result = (AutoMockMockService) beanFactory.getBean(beanName);

        assertThat(result).isNotNull();
        assertThat(MockUtil.isMock(result)).isTrue();
    }

    @Test
    void generateDefaultBeanNameSuccess() {
        final String result = AutoMockBeanUtils.generateDefaultBeanName((DefaultListableBeanFactory) beanFactory, clazz);
        assertThat(result).isEqualTo(beanName);
    }

}