package io.github.mangkyu.springboot.test.automock.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

@Slf4j
@UtilityClass
public final class AutoMockPropertyUtils {

    private static final String AUTO_MOCK_BASE_PACKAGE = "io.github.mangkyu.automock.target.basepackage";

    public static String findBasePackage(final ApplicationContext applicationContext) {
        return applicationContext.getEnvironment().getProperty(AUTO_MOCK_BASE_PACKAGE);
    }

}
