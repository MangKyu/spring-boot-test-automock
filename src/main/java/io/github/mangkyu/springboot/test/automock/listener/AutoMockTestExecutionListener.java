package io.github.mangkyu.springboot.test.automock.listener;

import io.github.mangkyu.springboot.test.automock.parser.AutoMockClassParser;
import org.mockito.internal.util.MockUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import java.util.List;

public class AutoMockTestExecutionListener extends AbstractTestExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(AutoMockTestExecutionListener.class);

    @Override
    public void beforeTestExecution(final TestContext testContext) {
        final List<Object> mockedFieldVariables = AutoMockClassParser.findMockedFieldVariables(testContext);
        for (final Object fieldVariable : mockedFieldVariables) {
            log.debug("reset mock, test class: {}, test method: {}, variable: {} ",
                    testContext.getTestClass().getSimpleName(), testContext.getTestMethod().getName(), MockUtil.getMockName(fieldVariable));
            MockUtil.resetMock(fieldVariable);
        }
    }

}
