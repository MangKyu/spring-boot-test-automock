package io.github.mangkyu.springboot.test.automock.properties;

public class AutoMockProperties {

    private final String basePackage;

    public AutoMockProperties(final String basePackage) {
        this.basePackage = basePackage;
    }

    public String getBasePackage() {
        return basePackage;
    }
}
