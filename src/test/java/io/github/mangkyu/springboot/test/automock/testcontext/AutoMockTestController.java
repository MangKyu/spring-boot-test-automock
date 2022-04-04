package io.github.mangkyu.springboot.test.automock.testcontext;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AutoMockTestController {

    private final AutoMockMockService mockService;

}
