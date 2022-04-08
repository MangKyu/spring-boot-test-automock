package io.github.mangkyu.springboot.test.automock.beanfactorypostprocessor;

import io.github.mangkyu.springboot.test.automock.properties.AutoMockProperties;
import io.github.mangkyu.springboot.test.automock.testcontext.AutoMockTestController;
import io.github.mangkyu.springboot.test.automock.utils.AutoMockBeanUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AutoMockBeanFactoryPostProcessorTest {

    private DefaultListableBeanFactory beanFactory;
    private AutoMockBeanFactoryPostProcessor beanFactoryPostProcessor;
    private final Class<?> clazz = AutoMockTestController.class;

    @BeforeEach
    void init() throws IOException {
        beanFactory = new DefaultListableBeanFactory();
        beanFactoryPostProcessor = new AutoMockBeanFactoryPostProcessor();

        final CachingMetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory();
        final MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(clazz.getName());
        final ScannedGenericBeanDefinition beanDefinition = new ScannedGenericBeanDefinition(metadataReader);

        final String beanName = AutoMockBeanUtils.generateDefaultBeanName(beanFactory, clazz);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
        beanFactory.registerSingleton(beanName, new AutoMockTestController(null));
    }

    @Test
    void postProcessBeanFactoryFail_AutoMockNotExists() {
        final IllegalStateException result = assertThrows(IllegalStateException.class, () -> beanFactoryPostProcessor.postProcessBeanFactory(beanFactory));

        assertThat(result).isNotNull();
    }

    @Test
    void postProcessBeanFactoryFail_AutoMockNotPropertiesNull() {
        registerAutoMockProperties("");

        final IllegalStateException result = assertThrows(IllegalStateException.class, () -> beanFactoryPostProcessor.postProcessBeanFactory(beanFactory));

        assertThat(result).isNotNull();
    }

    @Test
    void postProcessBeanFactorySuccess() {
        registerAutoMockProperties("mypackage");
        beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);

        assertThat(beanFactory.getSingletonNames().length).isEqualTo(clazz.getDeclaredConstructors()[0].getParameterTypes().length + 1);
    }

    @Test
    void findConstructorsOfScannedBeanSuccess() {
        final List<Constructor<?>> constructorList = beanFactoryPostProcessor.findConstructorsOfScannedBean(beanFactory);

        assertThat(constructorList.size()).isOne();
    }

    private void registerAutoMockProperties(final String value) {
        final String beanName = AutoMockBeanUtils.generateDefaultBeanName(beanFactory, AutoMockProperties.class);
        beanFactory.registerSingleton(beanName, new AutoMockProperties(value));
    }

}