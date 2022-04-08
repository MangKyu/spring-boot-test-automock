package io.github.mangkyu.springboot.test.automock.listener;

import io.github.mangkyu.springboot.test.automock.AutoMockApplication;
import io.github.mangkyu.springboot.test.automock.properties.AutoMockProperties;
import io.github.mangkyu.springboot.test.automock.utils.AutoMockBeanUtils;
import io.github.mangkyu.springboot.test.automock.utils.AutoMockPropertyUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

class AutoMockEventListenerTest {

    private AutoMockEventListener target;
    private ApplicationPreparedEvent event;
    private ApplicationContext applicationContext;
    private Class<?> clazz;

    @BeforeEach
    void init() {
        target = new AutoMockEventListener();
        applicationContext = new AnnotationConfigApplicationContext();
        clazz = AutoMockApplication.class;
        event = new ApplicationPreparedEvent(new SpringApplication(clazz), new String[0], (ConfigurableApplicationContext) applicationContext);
        ((ConfigurableApplicationContext) applicationContext).refresh();
    }

    @Test
    void propertyRegistered() {
        final String myBasePackage = "myBasePackage";
        System.setProperty(AutoMockPropertyUtils.AUTO_MOCK_BASE_PACKAGE, myBasePackage);
        target.onApplicationEvent(event);

        final DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        final AutoMockProperties result = (AutoMockProperties) beanFactory.getSingleton(AutoMockBeanUtils.generateDefaultBeanName(beanFactory, AutoMockProperties.class));

        assertThat(result).isNotNull();
        assertThat(result.getBasePackage()).isEqualTo(myBasePackage);
        System.clearProperty(AutoMockPropertyUtils.AUTO_MOCK_BASE_PACKAGE);
    }

    @Test
    void mainClassPackageRegistered() {
        target.onApplicationEvent(event);

        final DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        final AutoMockProperties result = (AutoMockProperties) beanFactory.getSingleton(AutoMockBeanUtils.generateDefaultBeanName(beanFactory, AutoMockProperties.class));

        assertThat(result).isNotNull();
        assertThat(result.getBasePackage()).isEqualTo(clazz.getPackage().getName());
    }

    @Test
    void alreadyRegistered() {
        final String basePackage = "alreadyRegistered";
        final DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        final String beanName = AutoMockBeanUtils.generateDefaultBeanName(beanFactory, AutoMockProperties.class);
        beanFactory.registerSingleton(beanName, new AutoMockProperties(basePackage));

        target.onApplicationEvent(event);
        final AutoMockProperties result = (AutoMockProperties) beanFactory.getSingleton(beanName);


        assertThat(result).isNotNull();
        assertThat(result.getBasePackage()).isEqualTo(basePackage);
    }

}