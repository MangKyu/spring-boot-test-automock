package com.mangkyu.springboot.test.automock.testcontext;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestContext;

import java.lang.reflect.Method;
import java.util.function.Function;

@Slf4j
public class AutoMockTestContext implements TestContext {

    private final ApplicationContext applicationContext;
    private final Class<?> testClass;
    private final Object testInstance;


    public AutoMockTestContext() {
        this.applicationContext = new AnnotationConfigApplicationContext();
        this.testClass = AutoMockTestControllerTest.class;

        try {
            this.testInstance = testClass.newInstance();
        } catch (final InstantiationException | IllegalAccessException e) {
            log.error("Initialize AutoMockTestContext Error", e);
            throw new RuntimeException();
        }
    }

    @Override
    public boolean hasApplicationContext() {
        return true;
    }

    @Override
    public void publishEvent(final Function<TestContext, ? extends ApplicationEvent> eventFactory) {

    }

    @Override
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public Class<?> getTestClass() {
        return testClass;
    }

    @Override
    public Object getTestInstance() {
        return testInstance;
    }

    @Override
    public Method getTestMethod() {
        return null;
    }

    @Override
    public Throwable getTestException() {
        return null;
    }

    @Override
    public void markApplicationContextDirty(final DirtiesContext.HierarchyMode hierarchyMode) {

    }

    @Override
    public void updateState(final Object testInstance, final Method testMethod, final Throwable testException) {

    }

    @Override
    public <T> T computeAttribute(final String name, final Function<String, T> computeFunction) {
        return TestContext.super.computeAttribute(name, computeFunction);
    }

    @Override
    public void setAttribute(final String name, final Object value) {

    }

    @Override
    public Object getAttribute(final String name) {
        return null;
    }

    @Override
    public Object removeAttribute(final String name) {
        return null;
    }

    @Override
    public boolean hasAttribute(final String name) {
        return false;
    }

    @Override
    public String[] attributeNames() {
        return new String[0];
    }
}
