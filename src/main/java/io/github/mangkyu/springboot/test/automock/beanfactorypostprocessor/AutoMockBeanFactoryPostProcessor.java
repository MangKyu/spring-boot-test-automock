package io.github.mangkyu.springboot.test.automock.beanfactorypostprocessor;

import io.github.mangkyu.springboot.test.automock.parser.AutoMockClassParser;
import io.github.mangkyu.springboot.test.automock.properties.AutoMockProperties;
import io.github.mangkyu.springboot.test.automock.utils.AutoMockBeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.util.StringUtils;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AutoMockBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    private static final Logger log = LoggerFactory.getLogger(AutoMockBeanFactoryPostProcessor.class);

    @Override
    public void postProcessBeanFactory(final ConfigurableListableBeanFactory beanFactory) throws BeansException {
        log.trace("postProcessBeanFactory start");
        final List<Constructor<?>> constructorList = findConstructorsOfScannedBean(beanFactory);
        final AutoMockProperties autoMockProperties = (AutoMockProperties) beanFactory.getSingleton(AutoMockBeanUtils.generateDefaultBeanName((BeanDefinitionRegistry) beanFactory, AutoMockProperties.class));
        if (autoMockProperties == null || !StringUtils.hasText(autoMockProperties.getBasePackage())) {
            throw new IllegalStateException("AutoMockProperties not found");
        }

        for (Constructor<?> v : constructorList) {
            for (Class<?> parameterClass : v.getParameterTypes()) {
                addUnregisteredCustomBeans(beanFactory, autoMockProperties, parameterClass);
            }
        }

        log.trace("postProcessBeanFactory complete");
    }

    private void addUnregisteredCustomBeans(final ConfigurableListableBeanFactory beanFactory, final AutoMockProperties autoMockProperties, final Class<?> parameterClass) {
        final String beanName = AutoMockBeanUtils.generateDefaultBeanName((BeanDefinitionRegistry) beanFactory, parameterClass);
        if (isUnregisteredCustomBean(beanFactory, autoMockProperties, parameterClass, beanName)) {
            AutoMockBeanUtils.registerSingletonMock(beanFactory, parameterClass, beanName);
        }
    }

    private boolean isUnregisteredCustomBean(final ConfigurableListableBeanFactory beanFactory, final AutoMockProperties autoMockProperties, final Class<?> parameterClass, final String beanName) {
        return !beanFactory.containsBean(beanName) && AutoMockClassParser.belongsToUserPackage(parameterClass, autoMockProperties.getBasePackage());
    }

    public List<Constructor<?>> findConstructorsOfScannedBean(final ConfigurableListableBeanFactory beanFactory) {
        return Arrays.stream(beanFactory.getBeanDefinitionNames())
                .map(beanFactory::getBeanDefinition)
                .filter(v -> v instanceof ScannedGenericBeanDefinition)
                .map(v -> AutoMockClassParser.findConstructor(v.getBeanClassName()))
                .collect(Collectors.toList());
    }

}
