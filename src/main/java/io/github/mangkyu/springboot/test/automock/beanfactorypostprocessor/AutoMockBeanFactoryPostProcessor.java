package io.github.mangkyu.springboot.test.automock.beanfactorypostprocessor;

import io.github.mangkyu.springboot.test.automock.parser.AutoMockClassParser;
import io.github.mangkyu.springboot.test.automock.properties.AutoMockProperties;
import io.github.mangkyu.springboot.test.automock.utils.AutoMockBeanUtils;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
public class AutoMockBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

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
                final String beanName = AutoMockBeanUtils.generateDefaultBeanName((BeanDefinitionRegistry) beanFactory, parameterClass);
                if (!beanFactory.containsBean(beanName) && parameterClass.getPackage().getName().contains(autoMockProperties.getBasePackage())) {
                    AutoMockBeanUtils.registerSingletonMock(beanFactory, parameterClass, beanName);
                }
            }
        }

        log.trace("postProcessBeanFactory complete");
    }

    public List<Constructor<?>> findConstructorsOfScannedBean(final ConfigurableListableBeanFactory beanFactory) {
        return Arrays.stream(beanFactory.getBeanDefinitionNames())
                .map(beanFactory::getBeanDefinition)
                .filter(v -> v instanceof ScannedGenericBeanDefinition)
                .map(v -> AutoMockClassParser.findConstructor(v.getBeanClassName()))
                .collect(Collectors.toList());
    }

}
