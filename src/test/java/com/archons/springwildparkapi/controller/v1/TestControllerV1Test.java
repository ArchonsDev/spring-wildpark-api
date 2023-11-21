package com.archons.springwildparkapi.controller.v1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(TestControllerV1.class)
class TestControllerV1Test {
    private final MockMvc mockMvc;

    @Autowired
    public TestControllerV1Test(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void testPingEndpoint() throws Exception {
        // Perform GET request to /api/v1/demo-controller/ping
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/demo-controller/ping")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Expect HTTP 200 OK
                .andExpect(MockMvcResultMatchers.content().string("pong!")); // Expect response body to be "pong!"
    }
}