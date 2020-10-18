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
public class ControllerLocationTest {
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LocationController locationController;

    @Test
    public void getAllLocationsTest() throws Exception
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

        mockMvc.perform(get("/user/locations").header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "{\"locationId\":1,\"locationName\":\"epol/brest\"}" +
                                ",{\"locationId\":2,\"locationName\":\"epol/minsk\"}" +
                                ",{\"locationId\":3,\"locationName\":\"epol/grodno\"}" +
                                ",{\"locationId\":4,\"locationName\":\"epol/lodz\"}" +
                                ",{\"locationId\":5,\"locationName\":\"epol/praga\"}"
                )));
    }

    @Test
    public void getLocationTest() throws Exception
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

        mockMvc.perform(get("/user/locations/{id}",5).header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"locationId\":5,\"locationName\":\"epol/praga\"}")));
    }
    @Test
    public void postLocationTest() throws Exception
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

        requestJson="{\n  \"locationName\" : \"Location9\"\n}";

        mockMvc.perform(post("/manager/locations")
                .contentType(APPLICATION_JSON_UTF8).content(requestJson)
                .header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"locationId\":6,\"locationName\":\"Location9\"}")));
    }

    @Test
    public void putLocationTest() throws Exception
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

        requestJson="{\r\n  \"locationId\" : 2,\r\n  \"locationName\" : \"Epol12\"\n}";

        mockMvc.perform(put("/manager/locations/{id}",2)
                .contentType(APPLICATION_JSON_UTF8).content(requestJson)
                .header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"locationId\":2,\"locationName\":\"Epol12\"")));
    }

    @Test
    public void deleteLocationTest() throws Exception
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

        mockMvc.perform(delete("/manager/locations/{id}",1).header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/user/locations/{id}",1).header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("")));

        mockMvc.perform(get("/user/locations").header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "{\"locationId\":2,\"locationName\":\"epol/minsk\"}" +
                                ",{\"locationId\":3,\"locationName\":\"epol/grodno\"}" +
                                ",{\"locationId\":4,\"locationName\":\"epol/lodz\"}" +
                                ",{\"locationId\":5,\"locationName\":\"epol/praga\"}"
                )));
    }
}
