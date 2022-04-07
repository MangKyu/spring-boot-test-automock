package io.github.mangkyu.springboot.test.automock.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

import static org.mockito.Mockito.mock;

@Slf4j
@UtilityClass
public final class AutoMockBeanUtils {

    private static final String AUTO_MOCK_BASE_PACKAGE = "io.github.mangkyu.automock.target.basepackage";

    public static void registerSingletonMock(final ConfigurableListableBeanFactory beanFactory, final Class<?> parameterType, final String beanName) {
        beanFactory.registerSingleton(beanName, mock(parameterType));
        log.debug("beanName: {} is registered Singleton", beanName);
    }

    public static String generateDefaultBeanName(final DefaultListableBeanFactory beanFactory, final Class<?> parameterType) {
        final RootBeanDefinition definition = new RootBeanDefinition(parameterType);
        return AnnotationBeanNameGenerator.INSTANCE.generateBeanName(definition, beanFactory);
    }

    public static String findBasePackage(final ApplicationContext applicationContext) {
        return applicationContext.getEnvironment().getProperty(AUTO_MOCK_BASE_PACKAGE);
    }

}
