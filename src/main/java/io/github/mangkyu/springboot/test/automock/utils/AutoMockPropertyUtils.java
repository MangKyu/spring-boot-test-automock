package io.github.mangkyu.springboot.test.automock.utils;

import org.springframework.context.ApplicationContext;

public final class AutoMockPropertyUtils {

    private AutoMockPropertyUtils() {
        throw new IllegalStateException();
    }

    public static final String AUTO_MOCK_BASE_PACKAGE = "io.github.mangkyu.automock.target.basepackage";

    public static String findBasePackage(final ApplicationContext applicationContext) {
        return applicationContext.getEnvironment().getProperty(AUTO_MOCK_BASE_PACKAGE);
    }

}
