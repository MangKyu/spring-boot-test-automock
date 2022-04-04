package com.mangkyu.springboot.test.automock.testcontext;

import org.springframework.stereotype.Service;

@Service
public class AutoMockMockService {

    public String call(final String input) {
        return input;
    }
}
