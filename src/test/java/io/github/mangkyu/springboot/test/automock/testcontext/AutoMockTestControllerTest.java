package io.github.mangkyu.springboot.test.automock.testcontext;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.mockito.Mockito.mock;

@WebMvcTest
public class AutoMockTestControllerTest {

    private AutoMockMockService mockService = mock(AutoMockMockService.class);

    private AutoMockMockService realService = new AutoMockMockService();

    @Test
    void sampleTest() {
    }

}
