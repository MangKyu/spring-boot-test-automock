package com.mangkyu.springboot.test.automock.listener;

import com.mangkyu.springboot.test.automock.parser.AutoMockClassParser;
import lombok.extern.slf4j.Slf4j;
import org.mockito.internal.util.MockUtil;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import java.util.List;

@Slf4j
public class AutoMockTestExecutionListener extends AbstractTestExecutionListener {

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
