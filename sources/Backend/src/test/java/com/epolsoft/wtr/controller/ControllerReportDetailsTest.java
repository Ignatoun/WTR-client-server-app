package com.epolsoft.wtr.controller;

import com.epolsoft.wtr.model.Enums.Status;
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
public class ControllerReportDetailsTest {

    @Autowired
    private MockMvc mockMvc;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Test
    public void testGetAllReportDetails() throws Exception {
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

        mockMvc.perform(get("/admin/reportDetails").header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "{\"reportDetailsId\":1,\"reportDetailsDate\":\"2019-11-01\",\"status\":null,\"factor\":{\"factorId\":8,\"factorName\":\"Day Off\"},\"location\":{\"locationId\":1,\"locationName\":\"epol/brest\"},\"hours\":null,\"workUnits\":null,\"detailedTask\":null,\"task\":null,\"feature\":null,\"project\":null,\"comments\":null}," +
                                "{\"reportDetailsId\":2,\"reportDetailsDate\":\"2018-12-02\",\"status\":\"REGISTERED\",\"factor\":{\"factorId\":1,\"factorName\":\"Standard\"},\"location\":{\"locationId\":1,\"locationName\":\"epol/brest\"},\"hours\":1.0,\"workUnits\":1.0,\"detailedTask\":{\"detailedTaskId\":1,\"detailedTaskName\":\"Creating database schema (fields).\"},\"task\":{\"taskId\":1,\"taskName\":\"development of database tables\"},\"feature\":{\"featureId\":1,\"featureName\":\"project database development\"},\"project\":{\"projectId\":1,\"projectName\":\"wtr lite for all companies and users\",\"startDate\":\"2019-11-02\",\"endDate\":\"2020-05-09\"},\"comments\":\"comment\"}," +
                                "{\"reportDetailsId\":3,\"reportDetailsDate\":\"2019-12-02\",\"status\":\"APPROVED\",\"factor\":{\"factorId\":1,\"factorName\":\"Standard\"},\"location\":{\"locationId\":1,\"locationName\":\"epol/brest\"},\"hours\":2.0,\"workUnits\":2.0,\"detailedTask\":{\"detailedTaskId\":2,\"detailedTaskName\":\"Creating database schema (relations).\"},\"task\":{\"taskId\":1,\"taskName\":\"development of database tables\"},\"feature\":{\"featureId\":1,\"featureName\":\"project database development\"},\"project\":{\"projectId\":1,\"projectName\":\"wtr lite for all companies and users\",\"startDate\":\"2019-11-02\",\"endDate\":\"2020-05-09\"},\"comments\":\"I did it\"}," +
                                "{\"reportDetailsId\":4,\"reportDetailsDate\":\"2019-12-02\",\"status\":\"APPROVED\",\"factor\":{\"factorId\":1,\"factorName\":\"Standard\"},\"location\":{\"locationId\":1,\"locationName\":\"epol/brest\"},\"hours\":5.0,\"workUnits\":5.0,\"detailedTask\":{\"detailedTaskId\":3,\"detailedTaskName\":\"Creating SQL script for database.\"},\"task\":{\"taskId\":2,\"taskName\":\"database table implementation\"},\"feature\":{\"featureId\":1,\"featureName\":\"project database development\"},\"project\":{\"projectId\":1,\"projectName\":\"wtr lite for all companies and users\",\"startDate\":\"2019-11-02\",\"endDate\":\"2020-05-09\"},\"comments\":null}," +
                                "{\"reportDetailsId\":5,\"reportDetailsDate\":\"2019-12-03\",\"status\":\"REGISTERED\",\"factor\":{\"factorId\":1,\"factorName\":\"Standard\"},\"location\":{\"locationId\":2,\"locationName\":\"epol/minsk\"},\"hours\":3.0,\"workUnits\":3.0,\"detailedTask\":{\"detailedTaskId\":4,\"detailedTaskName\":\"Disabling foreign key checking.\"},\"task\":{\"taskId\":3,\"taskName\":\"Creating test data for the database\"},\"feature\":{\"featureId\":2,\"featureName\":\"application composition development\"},\"project\":{\"projectId\":1,\"projectName\":\"wtr lite for all companies and users\",\"startDate\":\"2019-11-02\",\"endDate\":\"2020-05-09\"},\"comments\":\"Comment\"}," +
                                "{\"reportDetailsId\":6,\"reportDetailsDate\":\"2019-12-03\",\"status\":\"REGISTERED\",\"factor\":{\"factorId\":1,\"factorName\":\"Standard\"},\"location\":{\"locationId\":2,\"locationName\":\"epol/minsk\"},\"hours\":5.0,\"workUnits\":5.0,\"detailedTask\":{\"detailedTaskId\":5,\"detailedTaskName\":\"Creating SQL script for tests.\"},\"task\":{\"taskId\":3,\"taskName\":\"Creating test data for the database\"},\"feature\":{\"featureId\":2,\"featureName\":\"application composition development\"},\"project\":{\"projectId\":1,\"projectName\":\"wtr lite for all companies and users\",\"startDate\":\"2019-11-02\",\"endDate\":\"2020-05-09\"},\"comments\":null}")));
    }
    @Test
    public void testGetReportDetailsById() throws Exception {
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

        mockMvc.perform(get("/admin/reportDetails/{id}",1).header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "{\"reportDetailsId\":1,\"reportDetailsDate\":\"2019-11-01\",\"status\":null,\"factor\":{\"factorId\":8,\"factorName\":\"Day Off\"},\"location\":{\"locationId\":1,\"locationName\":\"epol/brest\"},\"hours\":null,\"workUnits\":null,\"detailedTask\":null,\"task\":null,\"feature\":null,\"project\":null,\"comments\":null}")));
    }

    @Test
    public void testFindByDate() throws Exception {
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

        mockMvc.perform(get("/admin/reportDetails/date/{date}","2019-12-02")
                .header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "{\"reportDetailsId\":3,\"reportDetailsDate\":\"2019-12-02\",\"status\":\"APPROVED\",\"factor\":{\"factorId\":1,\"factorName\":\"Standard\"},\"location\":{\"locationId\":1,\"locationName\":\"epol/brest\"},\"hours\":2.0,\"workUnits\":2.0,\"detailedTask\":{\"detailedTaskId\":2,\"detailedTaskName\":\"Creating database schema (relations).\"},\"task\":{\"taskId\":1,\"taskName\":\"development of database tables\"},\"feature\":{\"featureId\":1,\"featureName\":\"project database development\"},\"project\":{\"projectId\":1,\"projectName\":\"wtr lite for all companies and users\",\"startDate\":\"2019-11-02\",\"endDate\":\"2020-05-09\"},\"comments\":\"I did it\"}," +
                                "{\"reportDetailsId\":4,\"reportDetailsDate\":\"2019-12-02\",\"status\":\"APPROVED\",\"factor\":{\"factorId\":1,\"factorName\":\"Standard\"},\"location\":{\"locationId\":1,\"locationName\":\"epol/brest\"},\"hours\":5.0,\"workUnits\":5.0,\"detailedTask\":{\"detailedTaskId\":3,\"detailedTaskName\":\"Creating SQL script for database.\"},\"task\":{\"taskId\":2,\"taskName\":\"database table implementation\"},\"feature\":{\"featureId\":1,\"featureName\":\"project database development\"},\"project\":{\"projectId\":1,\"projectName\":\"wtr lite for all companies and users\",\"startDate\":\"2019-11-02\",\"endDate\":\"2020-05-09\"},\"comments\":null}")));
    }

    @Test
    public void testByFindDateBetween() throws Exception {
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

        mockMvc.perform(get("/admin/reportDetails/date/{dateStart}/{dateEnd}","2019-12-02","2019-12-03")
                .header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "{\"reportDetailsId\":3,\"reportDetailsDate\":\"2019-12-02\",\"status\":\"APPROVED\",\"factor\":{\"factorId\":1,\"factorName\":\"Standard\"},\"location\":{\"locationId\":1,\"locationName\":\"epol/brest\"},\"hours\":2.0,\"workUnits\":2.0,\"detailedTask\":{\"detailedTaskId\":2,\"detailedTaskName\":\"Creating database schema (relations).\"},\"task\":{\"taskId\":1,\"taskName\":\"development of database tables\"},\"feature\":{\"featureId\":1,\"featureName\":\"project database development\"},\"project\":{\"projectId\":1,\"projectName\":\"wtr lite for all companies and users\",\"startDate\":\"2019-11-02\",\"endDate\":\"2020-05-09\"},\"comments\":\"I did it\"}," +
                                "{\"reportDetailsId\":4,\"reportDetailsDate\":\"2019-12-02\",\"status\":\"APPROVED\",\"factor\":{\"factorId\":1,\"factorName\":\"Standard\"},\"location\":{\"locationId\":1,\"locationName\":\"epol/brest\"},\"hours\":5.0,\"workUnits\":5.0,\"detailedTask\":{\"detailedTaskId\":3,\"detailedTaskName\":\"Creating SQL script for database.\"},\"task\":{\"taskId\":2,\"taskName\":\"database table implementation\"},\"feature\":{\"featureId\":1,\"featureName\":\"project database development\"},\"project\":{\"projectId\":1,\"projectName\":\"wtr lite for all companies and users\",\"startDate\":\"2019-11-02\",\"endDate\":\"2020-05-09\"},\"comments\":null}," +
                                "{\"reportDetailsId\":5,\"reportDetailsDate\":\"2019-12-03\",\"status\":\"REGISTERED\",\"factor\":{\"factorId\":1,\"factorName\":\"Standard\"},\"location\":{\"locationId\":2,\"locationName\":\"epol/minsk\"},\"hours\":3.0,\"workUnits\":3.0,\"detailedTask\":{\"detailedTaskId\":4,\"detailedTaskName\":\"Disabling foreign key checking.\"},\"task\":{\"taskId\":3,\"taskName\":\"Creating test data for the database\"},\"feature\":{\"featureId\":2,\"featureName\":\"application composition development\"},\"project\":{\"projectId\":1,\"projectName\":\"wtr lite for all companies and users\",\"startDate\":\"2019-11-02\",\"endDate\":\"2020-05-09\"},\"comments\":\"Comment\"}," +
                                "{\"reportDetailsId\":6,\"reportDetailsDate\":\"2019-12-03\",\"status\":\"REGISTERED\",\"factor\":{\"factorId\":1,\"factorName\":\"Standard\"},\"location\":{\"locationId\":2,\"locationName\":\"epol/minsk\"},\"hours\":5.0,\"workUnits\":5.0,\"detailedTask\":{\"detailedTaskId\":5,\"detailedTaskName\":\"Creating SQL script for tests.\"},\"task\":{\"taskId\":3,\"taskName\":\"Creating test data for the database\"},\"feature\":{\"featureId\":2,\"featureName\":\"application composition development\"},\"project\":{\"projectId\":1,\"projectName\":\"wtr lite for all companies and users\",\"startDate\":\"2019-11-02\",\"endDate\":\"2020-05-09\"},\"comments\":null}")));
    }

    @Test
    public void testFindDaysWithoutReports() throws Exception {
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

        mockMvc.perform(get("/user/reportDetails/date/{month}/{year}",12,2019)
                .header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31]")));
    }

    @Test
    public void testCreateReportDetails() throws Exception {
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

        requestJson="{\n" +
                "  \"reportDetailsDate\": \"2020-01-31\",\n" +
                "  \"status\": \"PRIVATE\",\n" +
                "  \"factorId\": 1,\n" +
                "  \"locationId\": 1,\n" +
                "  \"hours\": 1,\n" +
                "  \"workUnits\": 1,\n" +
                "  \"detailedTaskId\": 1,\n" +
                "  \"taskId\": 1,\n" +
                "  \"featureId\": 1,\n" +
                "  \"projectId\": 1,\n" +
                "  \"comments\": \"string\"\n" +
                "}";

        mockMvc.perform(post("/user/reportDetails")
                .contentType(APPLICATION_JSON_UTF8).content(requestJson)
                .header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"reportDetailsId\":7,\"reportDetailsDate\":\"2020-01-31T00:00:00.000+0000\",\"status\":\"PRIVATE\",\"factor\":{\"factorId\":1,\"factorName\":\"Standard\"},\"location\":{\"locationId\":1,\"locationName\":\"epol/brest\"},\"hours\":1.0,\"workUnits\":1.0,\"detailedTask\":{\"detailedTaskId\":1,\"detailedTaskName\":\"Creating database schema (fields).\"},\"task\":{\"taskId\":1,\"taskName\":\"development of database tables\"},\"feature\":{\"featureId\":1,\"featureName\":\"project database development\"},\"project\":{\"projectId\":1,\"projectName\":\"wtr lite for all companies and users\",\"startDate\":\"2019-11-02\",\"endDate\":\"2020-05-09\"},\"comments\":\"string\"}")));
    }

    @Test
    public void testUpdateReportDetails() throws Exception {
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

        requestJson="{\n" +
                "  \"comments\": \"string\",\n" +
                "  \"detailedTaskId\": 2,\n" +
                "  \"factorId\": 2,\n" +
                "  \"featureId\": 2,\n" +
                "  \"hours\": 2,\n" +
                "  \"locationId\": 2,\n" +
                "  \"projectId\": 2,\n" +
                "  \"reportDetailsDate\": \"2020-01-31\",\n" +
                "  \"status\": \"PRIVATE\",\n" +
                "  \"taskId\": 2,\n" +
                "  \"usersId\": [\n" +
                "    2\n" +
                "  ],\n" +
                "  \"workUnits\": 2\n" +
                "}";

        mockMvc.perform(put("/admin/reportDetails/{id}",1)
                .contentType(APPLICATION_JSON_UTF8).content(requestJson)
                .header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "{" +
                                "\"reportDetailsId\":1," +
                                "\"reportDetailsDate\":\"2020-01-31T00:00:00.000+0000\"," +
                                "\"status\":\"PRIVATE\"," +
                                "\"factor\":{\"factorId\":2,\"factorName\":\"Overtime\"}," +
                                "\"location\":{\"locationId\":2,\"locationName\":\"epol/minsk\"}," +
                                "\"hours\":2.0," +
                                "\"workUnits\":2.0," +
                                "\"detailedTask\":{\"detailedTaskId\":2,\"detailedTaskName\":\"Creating database schema (relations).\"}," +
                                "\"task\":{\"taskId\":2,\"taskName\":\"database table implementation\"}," +
                                "\"feature\":{\"featureId\":2,\"featureName\":\"application composition development\"}," +
                                "\"project\":{\"projectId\":2,\"projectName\":\"the best game in the world!\",\"startDate\":\"2018-04-20\",\"endDate\":\"2021-12-31\"}," +
                                "\"comments\":\"string\"}")));
    }

    @Test
    public void testDeleteReportDetails() throws Exception {
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

        mockMvc.perform(delete("/admin/reportDetails/{id}",1).header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/admin/reportDetails/{id}",1).header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isInternalServerError());

        mockMvc.perform(get("/admin/reportDetails").header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "{\"reportDetailsId\":2,\"reportDetailsDate\":\"2018-12-02\",\"status\":\"REGISTERED\",\"factor\":{\"factorId\":1,\"factorName\":\"Standard\"},\"location\":{\"locationId\":1,\"locationName\":\"epol/brest\"},\"hours\":1.0,\"workUnits\":1.0,\"detailedTask\":{\"detailedTaskId\":1,\"detailedTaskName\":\"Creating database schema (fields).\"},\"task\":{\"taskId\":1,\"taskName\":\"development of database tables\"},\"feature\":{\"featureId\":1,\"featureName\":\"project database development\"},\"project\":{\"projectId\":1,\"projectName\":\"wtr lite for all companies and users\",\"startDate\":\"2019-11-02\",\"endDate\":\"2020-05-09\"},\"comments\":\"comment\"}," +
                                "{\"reportDetailsId\":3,\"reportDetailsDate\":\"2019-12-02\",\"status\":\"APPROVED\",\"factor\":{\"factorId\":1,\"factorName\":\"Standard\"},\"location\":{\"locationId\":1,\"locationName\":\"epol/brest\"},\"hours\":2.0,\"workUnits\":2.0,\"detailedTask\":{\"detailedTaskId\":2,\"detailedTaskName\":\"Creating database schema (relations).\"},\"task\":{\"taskId\":1,\"taskName\":\"development of database tables\"},\"feature\":{\"featureId\":1,\"featureName\":\"project database development\"},\"project\":{\"projectId\":1,\"projectName\":\"wtr lite for all companies and users\",\"startDate\":\"2019-11-02\",\"endDate\":\"2020-05-09\"},\"comments\":\"I did it\"}," +
                                "{\"reportDetailsId\":4,\"reportDetailsDate\":\"2019-12-02\",\"status\":\"APPROVED\",\"factor\":{\"factorId\":1,\"factorName\":\"Standard\"},\"location\":{\"locationId\":1,\"locationName\":\"epol/brest\"},\"hours\":5.0,\"workUnits\":5.0,\"detailedTask\":{\"detailedTaskId\":3,\"detailedTaskName\":\"Creating SQL script for database.\"},\"task\":{\"taskId\":2,\"taskName\":\"database table implementation\"},\"feature\":{\"featureId\":1,\"featureName\":\"project database development\"},\"project\":{\"projectId\":1,\"projectName\":\"wtr lite for all companies and users\",\"startDate\":\"2019-11-02\",\"endDate\":\"2020-05-09\"},\"comments\":null}," +
                                "{\"reportDetailsId\":5,\"reportDetailsDate\":\"2019-12-03\",\"status\":\"REGISTERED\",\"factor\":{\"factorId\":1,\"factorName\":\"Standard\"},\"location\":{\"locationId\":2,\"locationName\":\"epol/minsk\"},\"hours\":3.0,\"workUnits\":3.0,\"detailedTask\":{\"detailedTaskId\":4,\"detailedTaskName\":\"Disabling foreign key checking.\"},\"task\":{\"taskId\":3,\"taskName\":\"Creating test data for the database\"},\"feature\":{\"featureId\":2,\"featureName\":\"application composition development\"},\"project\":{\"projectId\":1,\"projectName\":\"wtr lite for all companies and users\",\"startDate\":\"2019-11-02\",\"endDate\":\"2020-05-09\"},\"comments\":\"Comment\"}," +
                                "{\"reportDetailsId\":6,\"reportDetailsDate\":\"2019-12-03\",\"status\":\"REGISTERED\",\"factor\":{\"factorId\":1,\"factorName\":\"Standard\"},\"location\":{\"locationId\":2,\"locationName\":\"epol/minsk\"},\"hours\":5.0,\"workUnits\":5.0,\"detailedTask\":{\"detailedTaskId\":5,\"detailedTaskName\":\"Creating SQL script for tests.\"},\"task\":{\"taskId\":3,\"taskName\":\"Creating test data for the database\"},\"feature\":{\"featureId\":2,\"featureName\":\"application composition development\"},\"project\":{\"projectId\":1,\"projectName\":\"wtr lite for all companies and users\",\"startDate\":\"2019-11-02\",\"endDate\":\"2020-05-09\"},\"comments\":null}")));
        }

    @Test
    public void testDeleteAllReportDetails() throws Exception {
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

            mockMvc.perform(delete("/admin/reportDetails").header("Authorization","Bearer_"+token))
                    .andDo(print())
                    .andExpect(status().isOk());

            mockMvc.perform(get("/admin/reportDetails").header("Authorization","Bearer_"+token))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string("[]"));
        }

    @Test
    public void testCreateListOfReportDetails() throws Exception {
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

        requestJson="[\n" +
                "  {\n" +
                "    \"comments\": \"Comment1\",\n" +
                "    \"detailedTaskId\": 2,\n" +
                "    \"factorId\": 3,\n" +
                "    \"featureId\": 2,\n" +
                "    \"hours\": 1,\n" +
                "    \"locationId\": 2,\n" +
                "    \"projectId\": 2,\n" +
                "    \"reportDetailsDate\": \"2019-02-15\",\n" +
                "    \"reportDetailsId\": 1,\n" +
                "    \"status\": \"PRIVATE\",\n" +
                "    \"taskId\": 2,\n" +
                "    \"usersId\": [\n" +
                "      1\n" +
                "    ],\n" +
                "    \"workUnits\": 1\n" +
                "  },\n" +
                "  {\n" +
                "    \"comments\": \"Comment2\",\n" +
                "    \"detailedTaskId\": 3,\n" +
                "    \"factorId\": 2,\n" +
                "    \"featureId\": 4,\n" +
                "    \"hours\": 2,\n" +
                "    \"locationId\": 2,\n" +
                "    \"projectId\": 1,\n" +
                "    \"reportDetailsDate\": \"2020-02-10\",\n" +
                "    \"reportDetailsId\": 1,\n" +
                "    \"status\": \"PRIVATE\",\n" +
                "    \"taskId\": 2,\n" +
                "    \"usersId\": [\n" +
                "      1\n" +
                "    ],\n" +
                "    \"workUnits\": 1\n" +
                "  }\n" +
                "]";

        mockMvc.perform(post("/user/reportDetails/list")
                .contentType(APPLICATION_JSON_UTF8).content(requestJson)
                .header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[{\"reportDetailsId\":7,\"reportDetailsDate\":\"2019-02-15T00:00:00.000+0000\",\"status\":\"PRIVATE\",\"factor\":{\"factorId\":3,\"factorName\":\"Vacation\"},\"location\":{\"locationId\":2,\"locationName\":\"epol/minsk\"},\"hours\":1.0,\"workUnits\":1.0,\"detailedTask\":{\"detailedTaskId\":2,\"detailedTaskName\":\"Creating database schema (relations).\"},\"task\":{\"taskId\":2,\"taskName\":\"database table implementation\"},\"feature\":{\"featureId\":2,\"featureName\":\"application composition development\"},\"project\":{\"projectId\":2,\"projectName\":\"the best game in the world!\",\"startDate\":\"2018-04-20\",\"endDate\":\"2021-12-31\"},\"comments\":\"Comment1\"},{\"reportDetailsId\":8,\"reportDetailsDate\":\"2020-02-10T00:00:00.000+0000\",\"status\":\"PRIVATE\",\"factor\":{\"factorId\":2,\"factorName\":\"Overtime\"},\"location\":{\"locationId\":2,\"locationName\":\"epol/minsk\"},\"hours\":2.0,\"workUnits\":1.0,\"detailedTask\":{\"detailedTaskId\":3,\"detailedTaskName\":\"Creating SQL script for database.\"},\"task\":{\"taskId\":2,\"taskName\":\"database table implementation\"},\"feature\":{\"featureId\":4,\"featureName\":\"realization of navigation in the project\"},\"project\":{\"projectId\":1,\"projectName\":\"wtr lite for all companies and users\",\"startDate\":\"2019-11-02\",\"endDate\":\"2020-05-09\"},\"comments\":\"Comment2\"}]")));
    }

    @Test
    public void testUpdateListOfReportDetails() throws Exception {
        String requestJson="" +
                "{\n" +
                "  \"username\" : \"123user123\",\n" +
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

        requestJson="[\n" +
                "  {\n" +
                "    \"comments\": \"Comment1\",\n" +
                "    \"detailedTaskId\": 2,\n" +
                "    \"factorId\": 3,\n" +
                "    \"featureId\": 2,\n" +
                "    \"hours\": 1,\n" +
                "    \"locationId\": 2,\n" +
                "    \"projectId\": 2,\n" +
                "    \"reportDetailsDate\": \"2019-12-10\",\n" +
                "    \"reportDetailsId\": 1,\n" +
                "    \"status\": \"PRIVATE\",\n" +
                "    \"taskId\": 2,\n" +
                "    \"usersId\": [\n" +
                "      1\n" +
                "    ],\n" +
                "    \"workUnits\": 1\n" +
                "  },\n" +
                "  {\n" +
                "    \"comments\": \"Comment2\",\n" +
                "    \"detailedTaskId\": 3,\n" +
                "    \"factorId\": 2,\n" +
                "    \"featureId\": 4,\n" +
                "    \"hours\": 2,\n" +
                "    \"locationId\": 2,\n" +
                "    \"projectId\": 1,\n" +
                "    \"reportDetailsDate\": \"2020-02-10\",\n" +
                "    \"reportDetailsId\": 2,\n" +
                "    \"status\": \"PRIVATE\",\n" +
                "    \"taskId\": 2,\n" +
                "    \"usersId\": [\n" +
                "      1\n" +
                "    ],\n" +
                "    \"workUnits\": 1\n" +
                "  }\n" +
                "]";

        mockMvc.perform(put("/user/reportDetails/list")
                .contentType(APPLICATION_JSON_UTF8).content(requestJson)
                .header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "{\"reportDetailsId\":1,\"reportDetailsDate\":\"2019-12-10T00:00:00.000+0000\",\"status\":\"PRIVATE\",\"factor\":{\"factorId\":3,\"factorName\":\"Vacation\"},\"location\":{\"locationId\":2,\"locationName\":\"epol/minsk\"},\"hours\":1.0,\"workUnits\":1.0,\"detailedTask\":{\"detailedTaskId\":2,\"detailedTaskName\":\"Creating database schema (relations).\"},\"task\":{\"taskId\":2,\"taskName\":\"database table implementation\"},\"feature\":{\"featureId\":2,\"featureName\":\"application composition development\"},\"project\":{\"projectId\":2,\"projectName\":\"the best game in the world!\",\"startDate\":\"2018-04-20\",\"endDate\":\"2021-12-31\"},\"comments\":\"Comment1\"}," +
                        "{\"reportDetailsId\":2,\"reportDetailsDate\":\"2020-02-10T00:00:00.000+0000\",\"status\":\"PRIVATE\",\"factor\":{\"factorId\":2,\"factorName\":\"Overtime\"},\"location\":{\"locationId\":2,\"locationName\":\"epol/minsk\"},\"hours\":2.0,\"workUnits\":1.0,\"detailedTask\":{\"detailedTaskId\":3,\"detailedTaskName\":\"Creating SQL script for database.\"},\"task\":{\"taskId\":2,\"taskName\":\"database table implementation\"},\"feature\":{\"featureId\":4,\"featureName\":\"realization of navigation in the project\"},\"project\":{\"projectId\":1,\"projectName\":\"wtr lite for all companies and users\",\"startDate\":\"2019-11-02\",\"endDate\":\"2020-05-09\"},\"comments\":\"Comment2\"}")));
    }

    @Test
    public void getReports() throws Exception{
        String requestJson="" +
                "{\n" +
                "  \"username\" : \"123user123\",\n" +
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

        mockMvc.perform((get("/user/reportDetails/filter")
                .param("status", "REGISTERED")
                .param("taskId","1"))
                .header("Authorization","Bearer_"+token))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "{" +
                                "\"reportDetailsId\":2," +
                                "\"reportDetailsDate\":\"2018-12-02\"," +
                                "\"status\":\"REGISTERED\"," +
                                "\"factor\":{\"factorId\":1,\"factorName\":\"Standard\"}," +
                                "\"location\":{\"locationId\":1,\"locationName\":\"epol/brest\"}," +
                                "\"hours\":1.0,\"workUnits\":1.0," +
                                "\"detailedTask\":{\"detailedTaskId\":1,\"detailedTaskName\":\"Creating database schema (fields).\"}," +
                                "\"task\":{\"taskId\":1,\"taskName\":\"development of database tables\"}," +
                                "\"feature\":{\"featureId\":1,\"featureName\":\"project database development\"}," +
                                "\"project\":{\"projectId\":1,\"projectName\":\"wtr lite for all companies and users\",\"startDate\":\"2019-11-02\",\"endDate\":\"2020-05-09\"}," +
                                "\"comments\":\"comment\"" +
                                "}")));
    }

    @Test
    public void getReportsByManagerFilter() throws Exception{
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

        mockMvc.perform((get("/manager/reportDetails/filter/{userId}",1)
                .param("status", "REGISTERED")
                .param("taskId","1"))
                .header("Authorization","Bearer_"+token))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "{" +
                                "\"reportDetailsId\":2," +
                                "\"reportDetailsDate\":\"2018-12-02\"," +
                                "\"status\":\"REGISTERED\"," +
                                "\"factor\":{\"factorId\":1,\"factorName\":\"Standard\"}," +
                                "\"location\":{\"locationId\":1,\"locationName\":\"epol/brest\"}," +
                                "\"hours\":1.0,\"workUnits\":1.0," +
                                "\"detailedTask\":{\"detailedTaskId\":1,\"detailedTaskName\":\"Creating database schema (fields).\"}," +
                                "\"task\":{\"taskId\":1,\"taskName\":\"development of database tables\"}," +
                                "\"feature\":{\"featureId\":1,\"featureName\":\"project database development\"}," +
                                "\"project\":{\"projectId\":1,\"projectName\":\"wtr lite for all companies and users\",\"startDate\":\"2019-11-02\",\"endDate\":\"2020-05-09\"}," +
                                "\"comments\":\"comment\"" +
                                "}")));
    }

    @Test
    public void getReportByDateAndUserIdTest() throws Exception{
        String requestJson="" +
                "{\n" +
                "  \"username\" : \"123user123\",\n" +
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

        mockMvc.perform((get("/user/reportDetails/{date}", "2019-12-02"))
                .header("Authorization","Bearer_"+token))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "{\"reportDetailsId\":3,\"reportDetailsDate\":\"2019-12-02\",\"status\":\"APPROVED\",\"factor\":{\"factorId\":1,\"factorName\":\"Standard\"},\"location\":{\"locationId\":1,\"locationName\":\"epol/brest\"},\"hours\":2.0,\"workUnits\":2.0,\"detailedTask\":{\"detailedTaskId\":2,\"detailedTaskName\":\"Creating database schema (relations).\"},\"task\":{\"taskId\":1,\"taskName\":\"development of database tables\"},\"feature\":{\"featureId\":1,\"featureName\":\"project database development\"},\"project\":{\"projectId\":1,\"projectName\":\"wtr lite for all companies and users\",\"startDate\":\"2019-11-02\",\"endDate\":\"2020-05-09\"},\"comments\":\"I did it\"}," +
                                "{\"reportDetailsId\":4,\"reportDetailsDate\":\"2019-12-02\",\"status\":\"APPROVED\",\"factor\":{\"factorId\":1,\"factorName\":\"Standard\"},\"location\":{\"locationId\":1,\"locationName\":\"epol/brest\"},\"hours\":5.0,\"workUnits\":5.0,\"detailedTask\":{\"detailedTaskId\":3,\"detailedTaskName\":\"Creating SQL script for database.\"},\"task\":{\"taskId\":2,\"taskName\":\"database table implementation\"},\"feature\":{\"featureId\":1,\"featureName\":\"project database development\"},\"project\":{\"projectId\":1,\"projectName\":\"wtr lite for all companies and users\",\"startDate\":\"2019-11-02\",\"endDate\":\"2020-05-09\"},\"comments\":null}")));
    }
}
