package com.wjh.wjhcommontest;

import com.wjh.wjhcommontest.controller.MockTestController;
import com.wjh.wjhcommontest.test.BaseMockTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class MockTest extends BaseMockTest<MockTestController> {
    @Test
    public void testMock() throws Exception {
        RequestBuilder request= MockMvcRequestBuilders.get("/testPublicMock").param("s","2131234")
            .accept(org.springframework.http.MediaType.APPLICATION_JSON);
        MvcResult result=getMockMvc().perform(request).andExpect(status().isOk()).andReturn();
/*        log.info("result: "+result.getResponse().getContentAsString());*/
    }
}
