# AutoMock: Enhance your @WebMvcTest performance

## What is AutoMock
SpringBoot supports @WebMvcTest annotation for convenience web layer(controller) slice test. When testing web layer, it is necessary to mock or spy beans that controller depends on using @MockBean and @SpyBean.
However test specific controller through @WebMvcTest with @MockBean or @SpyBean affects test performance because it requires to reload spring context. Also it makes test context to handle lots of application context while testing. 
<br/>

AutoMock will automatically generate mock objects that our component depends on through constructor parameter type. So it will allows your @WebMvcTest to reuse spring test context and enhance your @WebMvcTest test performance
<br/>

## How to use
Basically we will test our controller layers like below

ASIS: 
```java
@WebMvcTest(MockBeanController.class)
public class MockBeanTest {

    @MockBean
    private MockBeanService mockBeanService;
    
}
```

<br/>
<br/>

If you use AutoMock, Mock object will be created and registered in bean factory. So field injection through autowired is possible instead of @MockBean like below.
Be aware that controller is not specified in @WebMvcTest. Specify controller will also require spring test context to be created. So do not specify controllers. 

```java
@WebMvcTest
public class MockBeanTest {

    @Autowired
    private MockBeanService mockBeanService;
    
}
```


<br/>
<br/>


## Getting started
build.gradle
```gradle
testImplementation 'io.github.mangkyu:spring-boot-test-automock:0.0.4'
```

## Be aware
- Currently automock not supports spying
- Currently mock object is generated based on constructor parameter type
- As it does not specify controller, any controller's api can be called by mockmvc

