package com.mangkyu.springboot.test.automock.utils;

import com.mangkyu.springboot.test.automock.testcontext.AutoMockMockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.MockUtil;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import static org.assertj.core.api.Assertions.assertThat;

class AutoMockBeanUtilsTest {

    private DefaultListableBeanFactory beanFactory;
    private Class<AutoMockMockService> clazz;
    private String beanName;

    @BeforeEach
    void init() {
        beanFactory = new DefaultListableBeanFactory();
        clazz = AutoMockMockService.class;
        beanName = "autoMockMockService";
    }

    @Test
    void registerSingletonMockSuccess() {
        AutoMockBeanUtils.registerSingletonMock(beanFactory, clazz, beanName);

        final AutoMockMockService result = (AutoMockMockService) beanFactory.getBean(beanName);

        assertThat(result).isNotNull();
        assertThat(MockUtil.isMock(result)).isTrue();
    }

    @Test
    void generateDefaultBeanNameSuccess() {
        final String result = AutoMockBeanUtils.generateDefaultBeanName(beanFactory, clazz);
        assertThat(result).isEqualTo(beanName);
    }

}