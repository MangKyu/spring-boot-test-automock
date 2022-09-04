package io.github.mangkyu.springboot.test.automock.listener;

import io.github.mangkyu.springboot.test.automock.properties.AutoMockProperties;
import io.github.mangkyu.springboot.test.automock.utils.AutoMockBeanUtils;
import io.github.mangkyu.springboot.test.automock.utils.AutoMockPropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.util.StringUtils;

public class AutoMockEventListener implements ApplicationListener<ApplicationPreparedEvent> {

    private static final Logger log = LoggerFactory.getLogger(AutoMockEventListener.class);

    @Override
    public void onApplicationEvent(final ApplicationPreparedEvent event) {
        final DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) event.getApplicationContext().getBeanFactory();
        final String beanName = AutoMockBeanUtils.generateDefaultBeanName(beanFactory, AutoMockProperties.class);
        if (!beanFactory.containsBean(beanName)) {
            final AutoMockProperties autoMockProperties = new AutoMockProperties(findBasePackage(event));
            beanFactory.registerSingleton(beanName, autoMockProperties);
            log.debug("BeanName: {} is registered Singleton", beanName);
        } else {
            log.debug("BeanName: {} is already registered Singleton", beanName);
        }
    }

    private String findBasePackage(final ApplicationPreparedEvent event) {
        final String propertyBasePackage = AutoMockPropertyUtils.findBasePackage(event.getApplicationContext());
        return StringUtils.hasText(propertyBasePackage)
                ? propertyBasePackage
                : findBasePackageFromMainClass((SpringApplication) event.getSource());
    }

    private String findBasePackageFromMainClass(final SpringApplication springApplication) {
        return ((Class<?>) springApplication.getAllSources().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("MainClass Not Found")))
                .getPackage()
                .getName();
    }

}