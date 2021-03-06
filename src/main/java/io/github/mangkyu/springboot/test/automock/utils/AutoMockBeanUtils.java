package io.github.mangkyu.springboot.test.automock.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

import static org.mockito.Mockito.mock;

@Slf4j
@UtilityClass
public final class AutoMockBeanUtils {

    public static void registerSingletonMock(final ConfigurableListableBeanFactory beanFactory, final Class<?> parameterType, final String beanName) {
        beanFactory.registerSingleton(beanName, mock(parameterType));
        log.debug("Mock object beanName: {} is registered Singleton", beanName);
    }

    public static String generateDefaultBeanName(final BeanDefinitionRegistry beanFactory, final Class<?> parameterType) {
        final RootBeanDefinition definition = new RootBeanDefinition(parameterType);
        return AnnotationBeanNameGenerator.INSTANCE.generateBeanName(definition, beanFactory);
    }

}
