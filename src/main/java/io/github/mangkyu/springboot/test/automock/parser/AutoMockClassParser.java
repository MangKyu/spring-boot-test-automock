package io.github.mangkyu.springboot.test.automock.parser;

import io.github.mangkyu.springboot.test.automock.listener.AutoMockTestExecutionListener;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.mockito.internal.util.MockUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.test.context.TestContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@UtilityClass
public final class AutoMockClassParser {

    public static Constructor<?> findConstructor(final String beanClassName) {
        try {
            final Class<?> beanClass = AutoMockClassParser.class.getClassLoader().loadClass(beanClassName);
            return BeanUtils.getResolvableConstructor(beanClass);
        } catch (final ClassNotFoundException e) {
            log.debug("cannot find class: {}", beanClassName, e);
        }

        // return constructor that has no parameterTypes
        return AutoMockTestExecutionListener.class.getDeclaredConstructors()[0];
    }

    public static List<Object> findMockedFieldVariables(final TestContext testContext) {
        return Arrays.stream(findDeclaredFields(testContext))
                .peek(v -> v.setAccessible(true))
                .map(v -> findFieldVariables(testContext, v))
                .filter(MockUtil::isMock)
                .collect(Collectors.toList());
    }

    private static Field[] findDeclaredFields(final TestContext testContext) {
        return testContext.getTestClass().getDeclaredFields();
    }

    private static Object findFieldVariables(final TestContext testContext, final Field field) {
        try {
            return field.get(testContext.getTestInstance());
        } catch (IllegalAccessException e) {
            log.debug("cannot findFieldVariables: {}", field.getName(), e);
        }
        return null;
    }

}
