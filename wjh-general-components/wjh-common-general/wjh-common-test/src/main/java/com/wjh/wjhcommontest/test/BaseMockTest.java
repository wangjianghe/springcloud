package com.wjh.wjhcommontest.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.reflect.ParameterizedType;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MockServletContext.class)
@WebAppConfiguration
@Slf4j
public class BaseMockTest<T> {
    private MockMvc mockMvc;
    private String name;

    @Before
    public void setUp(){
        ParameterizedType superClass= (ParameterizedType) getClass().getGenericSuperclass();
        Class<T> type= (Class<T>) superClass.getActualTypeArguments()[0];
        name=type.getName();
        log.info("----------开始{} 的测试-------------",name);
        try {
            mockMvc= MockMvcBuilders.standaloneSetup(type.newInstance()).build();
        } catch (Exception e) {
           throw new RuntimeException(e);
        }
    }
    @After
    public void end(){
        log.info("-------------完成{} 的测试--------",name);
    }

    public MockMvc getMockMvc(){
        return mockMvc;
    }
}
