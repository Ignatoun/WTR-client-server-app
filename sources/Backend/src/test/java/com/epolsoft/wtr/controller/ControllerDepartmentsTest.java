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
public class ControllerDepartmentsTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DepartmentController departmentController;

    @Test
    public void getAllDepartmentsTest() throws Exception
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

        mockMvc.perform(get("/user/departments").header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "{\"departmentId\":1,\"departmentName\":\"Department1\"}" +
                                ",{\"departmentId\":2,\"departmentName\":\"Department2\"}" +
                                ",{\"departmentId\":3,\"departmentName\":\"Department3\"}" +
                                ",{\"departmentId\":4,\"departmentName\":\"Department4\"}" +
                                ",{\"departmentId\":5,\"departmentName\":\"Department5\"}")));
    }

    @Test
    public void getDepartmentTest() throws Exception
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

        mockMvc.perform(get("/user/departments/{id}",1).header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"departmentId\":1,\"departmentName\":\"Department1\"}")));
    }
    @Test
    public void postDepartmentTest() throws Exception
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

        requestJson="{\n  \"departmentName\" : \"Department1\"\n}";

        mockMvc.perform(post("/manager/departments").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson).header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"departmentId\":6,\"departmentName\":\"Department1\"}")));
    }

    @Test
    public void putDepartmentTest() throws Exception
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

        requestJson="{\r\n  \"departmentId\" : 1,\r\n  \"departmentName\" : \"Department15\"\n}";

        mockMvc.perform(put("/manager/departments/{id}",1)
                .contentType(APPLICATION_JSON_UTF8).content(requestJson).header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"departmentId\":1,\"departmentName\":\"Department15\"")));
    }

    @Test
    public void deleteDepartmentTest() throws Exception
    {
        String requestJson="" +
                "{\n" +
                "  \"username\" : \"rabbit\",\n" +
                "  \"password\" : \"password\"\n" +
                "}";

        String[] response = mockMvc.perform(post("/auth/login").contentType(APPLICATION_JSON_UTF8).content(requestJson)).andReturn().getResponse().getContentAsString().split("\"token\"");
        String token= response[1].substring(2,response[1].length()-2);

        mockMvc.perform(delete("/manager/departments/{id}",1).header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/user/departments/{id}",1).header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("")));

        mockMvc.perform(get("/user/departments").header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "{\"departmentId\":2,\"departmentName\":\"Department2\"}" +
                                ",{\"departmentId\":3,\"departmentName\":\"Department3\"}" +
                                ",{\"departmentId\":4,\"departmentName\":\"Department4\"}" +
                                ",{\"departmentId\":5,\"departmentName\":\"Department5\"}")));
    }
}
