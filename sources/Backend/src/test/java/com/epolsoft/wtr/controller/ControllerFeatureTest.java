package com.epolsoft.wtr.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value={"/create-all-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value={"/create-all-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ControllerFeatureTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FeatureController featureController;

    @Test
    public void getAllFeatureTest() throws Exception
    {
        String requestJson="" +
                "{\n" +
                "  \"username\" : \"rabbit\",\n" +
                "  \"password\" : \"password\"\n" +
                "}";

        String[] response = mockMvc.perform(post("/auth/login")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andReturn()
                .getResponse()
                .getContentAsString()
                .split("\"token\"");
        String token= response[1].substring(2,response[1].length()-2);

        mockMvc.perform(get("/user/features").header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "{\"featureId\":1,\"featureName\":\"project database development\",\"projectId\":1}," +
                                "{\"featureId\":2,\"featureName\":\"application composition development\",\"projectId\":1}," +
                                "{\"featureId\":3,\"featureName\":\"connecting modules to the project\",\"projectId\":1}," +
                                "{\"featureId\":4,\"featureName\":\"realization of navigation in the project\",\"projectId\":2}," +
                                "{\"featureId\":5,\"featureName\":\"UI realization\",\"projectId\":2}," +
                                "{\"featureId\":6,\"featureName\":\"neural network architecture development\",\"projectId\":2}," +
                                "{\"featureId\":7,\"featureName\":\"neural network architecture implementation\",\"projectId\":2}")));
    }

    @Test
    public void getFeatureTest() throws Exception
    {
        String requestJson="" +
                "{\n" +
                "  \"username\" : \"rabbit\",\n" +
                "  \"password\" : \"password\"\n" +
                "}";

        String[] response = mockMvc.perform(post("/auth/login")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andReturn()
                .getResponse()
                .getContentAsString()
                .split("\"token\"");
        String token= response[1].substring(2,response[1].length()-2);

        mockMvc.perform(get("/user/features/{id}",4).header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "{\"featureId\":4,\"featureName\":\"realization of navigation in the project\",\"projectId\":2}")));
    }
    @Test
    public void postFeatureTest() throws Exception
    {
        String requestJson="" +
                "{\n" +
                "  \"username\" : \"rabbit\",\n" +
                "  \"password\" : \"password\"\n" +
                "}";

        String[] response = mockMvc.perform(post("/auth/login")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andReturn()
                .getResponse()
                .getContentAsString()
                .split("\"token\"");
        String token= response[1].substring(2,response[1].length()-2);

        requestJson="{\n  \"featureId\":8,\"featureName\":\"feature5\",\"projectId\":1\n}";

        mockMvc.perform(post("/manager/features")
                .contentType(APPLICATION_JSON_UTF8).content(requestJson)
                .header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "{\"featureId\":8,\"featureName\":\"feature5\"}")));
    }

    @Test
    public void putFeatureTest() throws Exception
    {
        String requestJson="" +
                "{\n" +
                "  \"username\" : \"rabbit\",\n" +
                "  \"password\" : \"password\"\n" +
                "}";

        String[] response = mockMvc.perform(post("/auth/login")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andReturn()
                .getResponse()
                .getContentAsString()
                .split("\"token\"");
        String token= response[1].substring(2,response[1].length()-2);

        requestJson="{\n  \"featureId\":2,\"featureName\":\"feature12\",\"projectId\":1\n}";

        mockMvc.perform(put("/manager/features/{id}",2)
                .contentType(APPLICATION_JSON_UTF8).content(requestJson)
                .header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "{\"featureId\":2,\"featureName\":\"feature12\"}")));
    }

    @Test
    public void deleteFeatureTest() throws Exception
    {
        String requestJson="" +
                "{\n" +
                "  \"username\" : \"rabbit\",\n" +
                "  \"password\" : \"password\"\n" +
                "}";

        String[] response = mockMvc.perform(post("/auth/login")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andReturn()
                .getResponse()
                .getContentAsString()
                .split("\"token\"");
        String token= response[1].substring(2,response[1].length()-2);

        mockMvc.perform(delete("/manager/features/1").header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/user/features/1").header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("")));

        mockMvc.perform(get("/user/features").header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                                "{\"featureId\":2,\"featureName\":\"application composition development\",\"projectId\":1}," +
                                        "{\"featureId\":3,\"featureName\":\"connecting modules to the project\",\"projectId\":1}," +
                                        "{\"featureId\":4,\"featureName\":\"realization of navigation in the project\",\"projectId\":2}," +
                                        "{\"featureId\":5,\"featureName\":\"UI realization\",\"projectId\":2}," +
                                        "{\"featureId\":6,\"featureName\":\"neural network architecture development\",\"projectId\":2}," +
                                        "{\"featureId\":7,\"featureName\":\"neural network architecture implementation\",\"projectId\":2}")));
    }
}