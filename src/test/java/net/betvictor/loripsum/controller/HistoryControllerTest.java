package net.betvictor.loripsum.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.betvictor.loripsum.model.MessagesResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
public class HistoryControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    void testGetHistory() throws Exception {
        for (int i = 0; i < 11; i++) {
            mockMvc.perform(get("/betvictor/text")
                    .param("p", "1")
                    .param("l", "short"));
        }
        var resultActions = mockMvc.perform(get("/betvictor/history"))
                .andExpect(status().isOk());
        var mvcResult = resultActions.andReturn();
        assertNotNull(mvcResult.getResponse());
        var json = mvcResult.getResponse().getContentAsString();
        var response = new ObjectMapper().readValue(json, MessagesResponse.class);
        assertNotNull(response.getMessages());
        assertEquals(10, response.getMessages().size());
    }
}
