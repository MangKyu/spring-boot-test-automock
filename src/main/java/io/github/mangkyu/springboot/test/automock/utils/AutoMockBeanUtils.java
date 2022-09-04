package io.github.mangkyu.springboot.test.automock.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

import static org.mockito.Mockito.mock;

public final class AutoMockBeanUtils {

    private static final Logger log = LoggerFactory.getLogger(AutoMockBeanUtils.class);

    private AutoMockBeanUtils() {
        throw new IllegalStateException();
    }

    public static void registerSingletonMock(final ConfigurableListableBeanFactory beanFactory, final Class<?> parameterType, final String beanName) {
        beanFactory.registerSingleton(beanName, mock(parameterType));
        log.debug("Mock object beanName: {} is registered Singleton", beanName);
    }

    public static String generateDefaultBeanName(final BeanDefinitionRegistry beanFactory, final Class<?> parameterType) {
        final RootBeanDefinition definition = new RootBeanDefinition(parameterType);
        return AnnotationBeanNameGenerator.INSTANCE.generateBeanName(definition, beanFactory);
    }

}
