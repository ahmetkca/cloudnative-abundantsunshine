package com.corneliadavis.cloudnative;

import com.corneliadavis.cloudnative.config.CloudnativeApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudnativeApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties = {
        "com.corneliadavis.cloudnative.posts.secrets:forTests",
        "spring.cloud.config.enabled:false"})
@AutoConfigureMockMvc
@DirtiesContext
public class CloudnativeStatelessnessApplicationTests {

    @Autowired
    private  MockMvc mockMvc;

    @Test
    public void contextLoads() {
    }

    @Test
    public void	actuator() throws Exception {

        mockMvc.perform(get("/env"))
                .andExpect(status().isOk());
    }

    @Test
    public void	checkPostCounts() throws Exception {

        mockMvc.perform(get("/posts").param("secret", "forTests"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(4)));

    }

}
