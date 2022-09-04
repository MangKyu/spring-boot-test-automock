package io.github.mangkyu.springboot.test.automock.parser;

import io.github.mangkyu.springboot.test.automock.listener.AutoMockTestExecutionListener;
import io.github.mangkyu.springboot.test.automock.testcontext.AutoMockTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestContext;

import java.lang.reflect.Constructor;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AutoMockClassParserTest {

    private TestContext testContext;

    @BeforeEach
    void init() {
        testContext = new AutoMockTestContext();
    }

    @Test
    void findConstructorSuccess() {
        final Constructor<?> constructor = AutoMockClassParser.findConstructor(TestConstructorClass.class.getName());
        assertThat(constructor.getParameterTypes().length).isOne();
    }

    @Test
    void findConstructorSuccess_NotExistsClass() {
        final Constructor<?> constructor = AutoMockClassParser.findConstructor("NotExists");
        assertThat(constructor.getParameterTypes().length).isZero();
    }

    @Test
    void findMockedFieldVariables() {
        final List<Object> fieldList = AutoMockClassParser.findMockedFieldVariables(testContext);
        assertThat(fieldList.size()).isEqualTo(1);
    }

    @Test
    void belongsToUserPackage_True() {
        final String basePackage = "io.github.mangkyu.springboot.test.automock.listener";

        final boolean result = AutoMockClassParser.belongsToUserPackage(AutoMockTestExecutionListener.class, basePackage);
        assertThat(result).isTrue();
    }

    @Test
    void belongsToUserPackage_False() {
        final String basePackage = "io.github.mangkyu.springboot.test.automock.utils";

        final boolean result = AutoMockClassParser.belongsToUserPackage(AutoMockTestExecutionListener.class, basePackage);
        assertThat(result).isFalse();
    }

    private static class TestConstructorClass {
        private final String name;

        public TestConstructorClass(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}