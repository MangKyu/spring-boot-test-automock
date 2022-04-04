package io.github.mangkyu.springboot.test.automock.listener;

import io.github.mangkyu.springboot.test.automock.testcontext.AutoMockTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestContext;

class AutoMockTestExecutionListenerTest {

    private AutoMockTestExecutionListener listener;
    private TestContext testContext;

    @BeforeEach
    void init() {
        listener = new AutoMockTestExecutionListener();
        testContext = new AutoMockTestContext();
    }

    @Test
    void beforeTestExecution() {
        listener.beforeTestExecution(testContext);
    }

}