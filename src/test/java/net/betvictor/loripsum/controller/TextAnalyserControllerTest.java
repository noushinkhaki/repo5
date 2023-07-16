package net.betvictor.loripsum.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.betvictor.loripsum.model.ErrorResponse;
import net.betvictor.loripsum.model.TextAnalyserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
public class TextAnalyserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    void testAnalyseText() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/betvictor/text")
                .param("p", "2")
                .param("l", "short"))
                .andExpect(status().isOk());
        MvcResult mvcResult = resultActions.andReturn();
        assertNotNull(mvcResult.getResponse());
        String json = mvcResult.getResponse().getContentAsString();
        TextAnalyserResponse response = new ObjectMapper().readValue(json, TextAnalyserResponse.class);
        assertNotNull(response.getFreq_word());
        assertNotNull(response.getAvg_paragraph_size());
        assertNotNull(response.getAvg_paragraph_processing_time());
        assertNotNull(response.getTotal_processing_time());
    }

    @Test
    void testAnalyseTextInvalidParam() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/betvictor/text")
                .param("p", "2")
                .param("l", "invalid"))
                .andExpect(status().isBadRequest());
        MvcResult mvcResult = resultActions.andReturn();
        assertNotNull(mvcResult.getResponse());
        String json = mvcResult.getResponse().getContentAsString();
        ErrorResponse errorResponse = new ObjectMapper().readValue(json, ErrorResponse.class);
        assertTrue(errorResponse.getMessage().contains("Invalid \"l\" parameter"));
    }

    @Test
    void testAnalyseTextMissingParam() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/betvictor/text")
                .param("p", "2"))
                .andExpect(status().isBadRequest());
        MvcResult mvcResult = resultActions.andReturn();
        assertNotNull(mvcResult.getResponse());
        String json = mvcResult.getResponse().getContentAsString();
        ErrorResponse errorResponse = new ObjectMapper().readValue(json, ErrorResponse.class);
        assertTrue(errorResponse.getMessage().contains("Missing parameter"));
    }
}
