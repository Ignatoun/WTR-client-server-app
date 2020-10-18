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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value={"/create-all-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value={"/create-all-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ControllerDetailedTaskTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DetailedTaskController detailedTaskController;

    @Test
    public void getAllDetailedTaskTest() throws Exception
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

        mockMvc.perform(get("/user/detailedTasks").header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "{\"detailedTaskId\":1,\"detailedTaskName\":\"Creating database schema (fields).\",\"taskId\":1}," +
                                  "{\"detailedTaskId\":2,\"detailedTaskName\":\"Creating database schema (relations).\",\"taskId\":1}," +
                                  "{\"detailedTaskId\":3,\"detailedTaskName\":\"Creating SQL script for database.\",\"taskId\":2}," +
                                  "{\"detailedTaskId\":4,\"detailedTaskName\":\"Disabling foreign key checking.\",\"taskId\":3}," +
                                  "{\"detailedTaskId\":5,\"detailedTaskName\":\"Creating SQL script for tests.\",\"taskId\":3}," +
                                  "{\"detailedTaskId\":6,\"detailedTaskName\":\"Creating Node software with React.\",\"taskId\":4}," +
                                  "{\"detailedTaskId\":7,\"detailedTaskName\":\"Choosing SQL language for database.\",\"taskId\":6}," +
                                  "{\"detailedTaskId\":8,\"detailedTaskName\":\"Creating database schema.\",\"taskId\":6}," +
                                  "{\"detailedTaskId\":9,\"detailedTaskName\":\"Connecting Redux.\",\"taskId\":7}," +
                                  "{\"detailedTaskId\":10,\"detailedTaskName\":\"Creating structure of Spring project.\",\"taskId\":8}," +
                                  "{\"detailedTaskId\":11,\"detailedTaskName\":\"Fixing errors.\",\"taskId\":8}," +
                                  "{\"detailedTaskId\":12,\"detailedTaskName\":\"Creating ui form to Main menu.\",\"taskId\":10}," +
                                  "{\"detailedTaskId\":13,\"detailedTaskName\":\"Connection of actions with the Additional menu.\",\"taskId\":11}," +
                                  "{\"detailedTaskId\":14,\"detailedTaskName\":\"Lets skip this task.\",\"taskId\":12}," +
                                  "{\"detailedTaskId\":15,\"detailedTaskName\":\"Writing code for main window.\",\"taskId\":13}," +
                                  "{\"detailedTaskId\":16,\"detailedTaskName\":\"Connection libraries.\",\"taskId\":14}," +
                                  "{\"detailedTaskId\":17,\"detailedTaskName\":\"Testing of innovations.\",\"taskId\":16}," +
                                  "{\"detailedTaskId\":18,\"detailedTaskName\":\"Fix errors.\",\"taskId\":16}," +
                                  "{\"detailedTaskId\":19,\"detailedTaskName\":\"Without comments.\",\"taskId\":17}," +
                                  "{\"detailedTaskId\":20,\"detailedTaskName\":\"Choosing max number of smth.\",\"taskId\":19}")));
    }

    @Test
    public void getDetailedTaskTest() throws Exception
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

        mockMvc.perform(get("/user/detailedTasks/{id}",1).header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"detailedTaskId\":1,\"detailedTaskName\":\"Creating database schema (fields).\",\"taskId\":1}")));
    }
    @Test
    public void postDetailedTaskTest() throws Exception
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

        requestJson="{\n  \"detailedTaskId\":1,\"detailedTaskName\":\"Fixing tests.\",\"taskId\":1\n}";

        mockMvc.perform(post("/manager/detailedTasks")
                .contentType(APPLICATION_JSON_UTF8).content(requestJson)
                .header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"detailedTaskId\":21,\"detailedTaskName\":\"Fixing tests.\"}")));
    }

    @Test
    public void putDetailedTaskTest() throws Exception
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

        requestJson="{\n  \"detailedTaskId\":1,\"detailedTaskName\":\"Fixing tests.\",\"taskId\":1\n}";

        mockMvc.perform(put("/manager/detailedTasks/{id}",1)
                .contentType(APPLICATION_JSON_UTF8).content(requestJson)
                .header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"detailedTaskId\":1,\"detailedTaskName\":\"Fixing tests.\"")));
    }

    @Test
    public void deleteDetailedTaskTest() throws Exception
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

        mockMvc.perform(delete("/manager/detailedTasks/{id}",1).header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/user/detailedTasks/{id}",1).header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("")));

        mockMvc.perform(get("/user/detailedTasks").header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "{\"detailedTaskId\":2,\"detailedTaskName\":\"Creating database schema (relations).\",\"taskId\":1}," +
                                "{\"detailedTaskId\":3,\"detailedTaskName\":\"Creating SQL script for database.\",\"taskId\":2}," +
                                "{\"detailedTaskId\":4,\"detailedTaskName\":\"Disabling foreign key checking.\",\"taskId\":3}," +
                                "{\"detailedTaskId\":5,\"detailedTaskName\":\"Creating SQL script for tests.\",\"taskId\":3}," +
                                "{\"detailedTaskId\":6,\"detailedTaskName\":\"Creating Node software with React.\",\"taskId\":4}," +
                                "{\"detailedTaskId\":7,\"detailedTaskName\":\"Choosing SQL language for database.\",\"taskId\":6}," +
                                "{\"detailedTaskId\":8,\"detailedTaskName\":\"Creating database schema.\",\"taskId\":6}," +
                                "{\"detailedTaskId\":9,\"detailedTaskName\":\"Connecting Redux.\",\"taskId\":7}," +
                                "{\"detailedTaskId\":10,\"detailedTaskName\":\"Creating structure of Spring project.\",\"taskId\":8}," +
                                "{\"detailedTaskId\":11,\"detailedTaskName\":\"Fixing errors.\",\"taskId\":8}," +
                                "{\"detailedTaskId\":12,\"detailedTaskName\":\"Creating ui form to Main menu.\",\"taskId\":10}," +
                                "{\"detailedTaskId\":13,\"detailedTaskName\":\"Connection of actions with the Additional menu.\",\"taskId\":11}," +
                                "{\"detailedTaskId\":14,\"detailedTaskName\":\"Lets skip this task.\",\"taskId\":12}," +
                                "{\"detailedTaskId\":15,\"detailedTaskName\":\"Writing code for main window.\",\"taskId\":13}," +
                                "{\"detailedTaskId\":16,\"detailedTaskName\":\"Connection libraries.\",\"taskId\":14}," +
                                "{\"detailedTaskId\":17,\"detailedTaskName\":\"Testing of innovations.\",\"taskId\":16}," +
                                "{\"detailedTaskId\":18,\"detailedTaskName\":\"Fix errors.\",\"taskId\":16}," +
                                "{\"detailedTaskId\":19,\"detailedTaskName\":\"Without comments.\",\"taskId\":17}," +
                                "{\"detailedTaskId\":20,\"detailedTaskName\":\"Choosing max number of smth.\",\"taskId\":19}")));
    }
}
