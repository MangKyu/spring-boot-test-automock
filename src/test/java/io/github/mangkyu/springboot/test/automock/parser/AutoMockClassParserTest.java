package io.github.mangkyu.springboot.test.automock.parser;

import io.github.mangkyu.springboot.test.automock.testcontext.AutoMockTestContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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

    @Getter
    @RequiredArgsConstructor
    private static class TestConstructorClass {
        private final String name;
    }

}