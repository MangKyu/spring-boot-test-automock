package io.github.mangkyu.springboot.test.automock.parser;

import io.github.mangkyu.springboot.test.automock.listener.AutoMockTestExecutionListener;
import org.mockito.internal.util.MockUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.test.context.TestContext;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class AutoMockClassParser {

    private static final Logger log = LoggerFactory.getLogger(AutoMockClassParser.class);
    private static final ClassLoader CLASS_LOADER = AutoMockClassParser.class.getClassLoader();

    private AutoMockClassParser() {
        throw new IllegalStateException();
    }

    public static Constructor<?> findConstructor(final String beanClassName) {
        try {
            final Class<?> beanClass = CLASS_LOADER.loadClass(beanClassName);
            return BeanUtils.getResolvableConstructor(beanClass);
        } catch (final ClassNotFoundException e) {
            log.debug("cannot find class: {}", beanClassName, e);
        }

        // return constructor that has no parameterTypes
        return AutoMockTestExecutionListener.class.getDeclaredConstructors()[0];
    }

    public static List<Object> findMockedFieldVariables(final TestContext testContext) {
        return Arrays.stream(findDeclaredFields(testContext))
                .map(v -> ReflectionTestUtils.getField(testContext.getTestInstance(), v.getName()))
                .filter(MockUtil::isMock)
                .collect(Collectors.toList());
    }

    private static Field[] findDeclaredFields(final TestContext testContext) {
        return testContext.getTestClass().getDeclaredFields();
    }

    public static boolean belongsToUserPackage(final Class<?> parameterClass, final String basePackage) {
        return parameterClass.getPackage().getName().contains(basePackage);
    }

}
