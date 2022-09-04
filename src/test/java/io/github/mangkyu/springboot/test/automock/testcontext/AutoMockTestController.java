package io.github.mangkyu.springboot.test.automock.testcontext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutoMockTestController {

    private final AutoMockMockService mockService;

    @Autowired
    public AutoMockTestController(final AutoMockMockService mockService) {
        this.mockService = mockService;
    }
}
