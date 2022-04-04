package io.github.mangkyu.springboot.test.automock.listener;

import io.github.mangkyu.springboot.test.automock.testcontext.AutoMockMockService;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.MockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@WebMvcTest
public class InitMockTest {

    @Autowired
    private AutoMockMockService autoMockMockService;

    @Test
    void isMocked() {
        assertThat(MockUtil.isMock(autoMockMockService)).isTrue();
    }

    @Test
    void call1() {
        doReturn("A").when(autoMockMockService).call("C");

        final String result = autoMockMockService.call("C");
        assertThat(result).isEqualTo("A");
    }

    @Test
    void call2() {
        doReturn("B").when(autoMockMockService).call("C");

        final String result = autoMockMockService.call("C");

        assertThat(result).isEqualTo("B");

    }

    @Test
    void call3() {
        final String result = autoMockMockService.call("C");

        assertThat(result).isNull();
        doReturn("C").when(autoMockMockService).call("C");
    }

    @Test
    void call4() {
        final String result = autoMockMockService.call("C");
        assertThat(result).isNull();
    }

}
