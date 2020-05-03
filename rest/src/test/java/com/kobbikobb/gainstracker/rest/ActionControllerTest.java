package com.kobbikobb.gainstracker.rest;

import com.jayway.jsonpath.JsonPath;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ActionControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldAddAction() throws Exception {
        String actionInput = new JSONObject()
                .put("name", "Burpees")
                .put("unit", "Count").toString();

        mvc.perform(MockMvcRequestBuilders
                .post("/actions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(actionInput))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name", equalTo("Burpees")))
                .andExpect(jsonPath("$.unit", equalTo("Count")))
        ;
    }

    @Test
    public void shouldGetActions() throws Exception {
        createAnyAction();

        mvc.perform(MockMvcRequestBuilders
                .get("/actions")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    public void shouldDeleteAction() throws Exception {
        Integer actionId = createAnyAction();

        mvc.perform(MockMvcRequestBuilders
                .delete("/actions/" + actionId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
        ;
    }

    private Integer createAnyAction() throws Exception {
        String actionInput = new JSONObject()
                .put("name", "Burpees")
                .put("unit", "Count").toString();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .post("/actions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(actionInput))
                .andExpect(status().isOk())
                .andReturn();
        mvcResult.getResponse().getContentAsString();

        return JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.id");
    }
}
