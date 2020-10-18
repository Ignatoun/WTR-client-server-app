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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value={"/create-all-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value={"/create-all-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ControllerTaskTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskController taskController;

    @Test
    public void getAllTaskTest() throws Exception
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

        mockMvc.perform(get("/user/tasks").header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "{\"taskId\":1,\"taskName\":\"development of database tables\",\"featureId\":1}," +
                                "{\"taskId\":2,\"taskName\":\"database table implementation\",\"featureId\":1}," +
                                "{\"taskId\":3,\"taskName\":\"Creating test data for the database\",\"featureId\":1}," +
                                "{\"taskId\":4,\"taskName\":\"frontend\",\"featureId\":2}," +
                                "{\"taskId\":5,\"taskName\":\"backend\",\"featureId\":2}," +
                                "{\"taskId\":6,\"taskName\":\"database\",\"featureId\":2}," +
                                "{\"taskId\":7,\"taskName\":\"to frontend\",\"featureId\":3}," +
                                "{\"taskId\":8,\"taskName\":\"to backend\",\"featureId\":3}," +
                                "{\"taskId\":9,\"taskName\":\"to database\",\"featureId\":3}," +
                                "{\"taskId\":10,\"taskName\":\"main menu\",\"featureId\":4}," +
                                "{\"taskId\":11,\"taskName\":\"addition menu\",\"featureId\":4}," +
                                "{\"taskId\":12,\"taskName\":\"prototyping\",\"featureId\":5}," +
                                "{\"taskId\":13,\"taskName\":\"construction\",\"featureId\":5}," +
                                "{\"taskId\":14,\"taskName\":\"used modules\",\"featureId\":6}," +
                                "{\"taskId\":15,\"taskName\":\"advantages\",\"featureId\":6}," +
                                "{\"taskId\":16,\"taskName\":\"innovations\",\"featureId\":6}," +
                                "{\"taskId\":17,\"taskName\":\"borrowing architecture\",\"featureId\":6}," +
                                "{\"taskId\":18,\"taskName\":\"main application\",\"featureId\":7}," +
                                "{\"taskId\":19,\"taskName\":\"sampling\",\"featureId\":7}," +
                                "{\"taskId\":20,\"taskName\":\"training\",\"featureId\":7}," +
                                "{\"taskId\":21,\"taskName\":\"comparison of results\",\"featureId\":7}")));
    }

    @Test
    public void getTaskTest() throws Exception
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

        mockMvc.perform(get("/user/tasks/{id}",1).header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"taskId\":1,\"taskName\":\"development of database tables\",\"featureId\":1}")));
    }
    @Test
    public void postTaskTest() throws Exception
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

        requestJson="{\n  \"taskName\" : \"Task1\", \n  \"featureId\" : 4 \n}";

        mockMvc.perform(post("/manager/tasks")
                .contentType(APPLICATION_JSON_UTF8).content(requestJson)
                .header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"taskId\":22,\"taskName\":\"Task1\"}")));
    }

    @Test
    public void putTaskTest() throws Exception
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

        requestJson="{\n  \"taskId:\" : 1,\n  \"taskName\" : \"Task1\",\n  \"featureId\" : 1 \n}";

        mockMvc.perform(put("/manager/tasks/{id}",1)
                .contentType(APPLICATION_JSON_UTF8).content(requestJson)
                .header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"taskId\":1,\"taskName\":\"Task1\"}")));
    }

    @Test
    public void deleteTaskTest() throws Exception
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

        mockMvc.perform(delete("/manager/tasks/{id}",1).header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/user/tasks/{id}",1).header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("")));

        mockMvc.perform(get("/user/tasks").header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "{\"taskId\":2,\"taskName\":\"database table implementation\",\"featureId\":1}," +
                                "{\"taskId\":3,\"taskName\":\"Creating test data for the database\",\"featureId\":1}," +
                                "{\"taskId\":4,\"taskName\":\"frontend\",\"featureId\":2}," +
                                "{\"taskId\":5,\"taskName\":\"backend\",\"featureId\":2}," +
                                "{\"taskId\":6,\"taskName\":\"database\",\"featureId\":2}," +
                                "{\"taskId\":7,\"taskName\":\"to frontend\",\"featureId\":3}," +
                                "{\"taskId\":8,\"taskName\":\"to backend\",\"featureId\":3}," +
                                "{\"taskId\":9,\"taskName\":\"to database\",\"featureId\":3}," +
                                "{\"taskId\":10,\"taskName\":\"main menu\",\"featureId\":4}," +
                                "{\"taskId\":11,\"taskName\":\"addition menu\",\"featureId\":4}," +
                                "{\"taskId\":12,\"taskName\":\"prototyping\",\"featureId\":5}," +
                                "{\"taskId\":13,\"taskName\":\"construction\",\"featureId\":5}," +
                                "{\"taskId\":14,\"taskName\":\"used modules\",\"featureId\":6}," +
                                "{\"taskId\":15,\"taskName\":\"advantages\",\"featureId\":6}," +
                                "{\"taskId\":16,\"taskName\":\"innovations\",\"featureId\":6}," +
                                "{\"taskId\":17,\"taskName\":\"borrowing architecture\",\"featureId\":6}," +
                                "{\"taskId\":18,\"taskName\":\"main application\",\"featureId\":7}," +
                                "{\"taskId\":19,\"taskName\":\"sampling\",\"featureId\":7}," +
                                "{\"taskId\":20,\"taskName\":\"training\",\"featureId\":7}," +
                                "{\"taskId\":21,\"taskName\":\"comparison of results\",\"featureId\":7}")));
    }
}
