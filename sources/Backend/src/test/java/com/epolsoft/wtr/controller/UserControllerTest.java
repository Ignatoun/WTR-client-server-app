package com.epolsoft.wtr.controller;

import com.epolsoft.wtr.dao.UserDAO;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value={"/create-all-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value={"/create-all-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserDAO userDAO;

    @Test
    public void getAllUsersTest() throws Exception {
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

        mockMvc.perform(get("/manager/users").header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "{\"userId\":1,\"userName\":\"123user123\",\"firstName\":\"Ivan\",\"lastName\":\"Ivanov\",\"email\":\"vania@tut.by\",\"title\":{\"titleId\":1,\"titleName\":\"Software Developer\"},\"department\":{\"departmentId\":1,\"departmentName\":\"Department1\"},\"roles\":[{\"roleId\":1,\"roleName\":\"USER\"}]}," +
                                "{\"userId\":2,\"userName\":\"pro-developer\",\"firstName\":\"Petr\",\"lastName\":\"Petrov\",\"email\":\"petr@mail.ru\",\"title\":{\"titleId\":2,\"titleName\":\"Software Tester\"},\"department\":{\"departmentId\":1,\"departmentName\":\"Department1\"},\"roles\":[{\"roleId\":2,\"roleName\":\"ADMIN\"}]}," +
                                "{\"userId\":3,\"userName\":\"bbig-boss\",\"firstName\":\"Sergey\",\"lastName\":\"Ivanov\",\"email\":\"boss@gmail.com\",\"title\":{\"titleId\":5,\"titleName\":\"Network Administrator\"},\"department\":{\"departmentId\":1,\"departmentName\":\"Department1\"},\"roles\":[{\"roleId\":3,\"roleName\":\"MANAGER\"}]}," +
                                "{\"userId\":4,\"userName\":\"american-man\",\"firstName\":\"Mike\",\"lastName\":\"Djonson\",\"email\":\"mike@outlook.by\",\"title\":{\"titleId\":4,\"titleName\":\"Software Engineer\"},\"department\":{\"departmentId\":1,\"departmentName\":\"Department1\"},\"roles\":[{\"roleId\":1,\"roleName\":\"USER\"}]}," +
                                "{\"userId\":5,\"userName\":\"1-lopata-1\",\"firstName\":\"Artem\",\"lastName\":\"Lopata\",\"email\":\"shovel@tut.by\",\"title\":{\"titleId\":3,\"titleName\":\"Data Architect\"},\"department\":{\"departmentId\":1,\"departmentName\":\"Department1\"},\"roles\":[{\"roleId\":1,\"roleName\":\"USER\"}]}," +
                                "{\"userId\":6,\"userName\":\"the-best\",\"firstName\":\"Astra\",\"lastName\":\"Mironova\",\"email\":\"miron@mail.ru\",\"title\":{\"titleId\":2,\"titleName\":\"Software Tester\"},\"department\":{\"departmentId\":1,\"departmentName\":\"Department1\"},\"roles\":[{\"roleId\":1,\"roleName\":\"USER\"}]}," +
                                "{\"userId\":7,\"userName\":\"gold-girl\",\"firstName\":\"Zlata\",\"lastName\":\"Maximochkina\",\"email\":\"zlata_max@gmail.com\",\"title\":{\"titleId\":5,\"titleName\":\"Network Administrator\"},\"department\":{\"departmentId\":1,\"departmentName\":\"Department1\"},\"roles\":[{\"roleId\":1,\"roleName\":\"USER\"}]}," +
                                "{\"userId\":8,\"userName\":\"lihach\",\"firstName\":\"Daria\",\"lastName\":\"Lihacheva\",\"email\":\"lihach@tut.by\",\"title\":{\"titleId\":1,\"titleName\":\"Software Developer\"},\"department\":{\"departmentId\":1,\"departmentName\":\"Department1\"},\"roles\":[{\"roleId\":1,\"roleName\":\"USER\"}]}," +
                                "{\"userId\":9,\"userName\":\"natasha\",\"firstName\":\"Natalia\",\"lastName\":\"Romanova\",\"email\":\"nataly@outlook.com\",\"title\":{\"titleId\":3,\"titleName\":\"Data Architect\"},\"department\":{\"departmentId\":1,\"departmentName\":\"Department1\"},\"roles\":[{\"roleId\":1,\"roleName\":\"USER\"}]}," +
                                "{\"userId\":10,\"userName\":\"rabbit\",\"firstName\":\"Anastasia\",\"lastName\":\"Safonova\",\"email\":\"nastya@gmail.com\",\"title\":{\"titleId\":4,\"titleName\":\"Software Engineer\"},\"department\":{\"departmentId\":1,\"departmentName\":\"Department1\"},\"roles\":[{\"roleId\":2,\"roleName\":\"ADMIN\"}]}")));
    }

    @Test
    public void getUsersByFilterTest() throws Exception {
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

        mockMvc.perform(get("/manager/users/filter").header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "{\"userId\":1,\"userName\":\"123user123\",\"firstName\":\"Ivan\",\"lastName\":\"Ivanov\",\"email\":\"vania@tut.by\",\"title\":{\"titleId\":1,\"titleName\":\"Software Developer\"},\"department\":{\"departmentId\":1,\"departmentName\":\"Department1\"},\"roles\":[{\"roleId\":1,\"roleName\":\"USER\"}]}," +
                                "{\"userId\":2,\"userName\":\"pro-developer\",\"firstName\":\"Petr\",\"lastName\":\"Petrov\",\"email\":\"petr@mail.ru\",\"title\":{\"titleId\":2,\"titleName\":\"Software Tester\"},\"department\":{\"departmentId\":1,\"departmentName\":\"Department1\"},\"roles\":[{\"roleId\":2,\"roleName\":\"ADMIN\"}]}," +
                                "{\"userId\":3,\"userName\":\"bbig-boss\",\"firstName\":\"Sergey\",\"lastName\":\"Ivanov\",\"email\":\"boss@gmail.com\",\"title\":{\"titleId\":5,\"titleName\":\"Network Administrator\"},\"department\":{\"departmentId\":1,\"departmentName\":\"Department1\"},\"roles\":[{\"roleId\":3,\"roleName\":\"MANAGER\"}]}," +
                                "{\"userId\":4,\"userName\":\"american-man\",\"firstName\":\"Mike\",\"lastName\":\"Djonson\",\"email\":\"mike@outlook.by\",\"title\":{\"titleId\":4,\"titleName\":\"Software Engineer\"},\"department\":{\"departmentId\":1,\"departmentName\":\"Department1\"},\"roles\":[{\"roleId\":1,\"roleName\":\"USER\"}]}," +
                                "{\"userId\":5,\"userName\":\"1-lopata-1\",\"firstName\":\"Artem\",\"lastName\":\"Lopata\",\"email\":\"shovel@tut.by\",\"title\":{\"titleId\":3,\"titleName\":\"Data Architect\"},\"department\":{\"departmentId\":1,\"departmentName\":\"Department1\"},\"roles\":[{\"roleId\":1,\"roleName\":\"USER\"}]}," +
                                "{\"userId\":6,\"userName\":\"the-best\",\"firstName\":\"Astra\",\"lastName\":\"Mironova\",\"email\":\"miron@mail.ru\",\"title\":{\"titleId\":2,\"titleName\":\"Software Tester\"},\"department\":{\"departmentId\":1,\"departmentName\":\"Department1\"},\"roles\":[{\"roleId\":1,\"roleName\":\"USER\"}]}," +
                                "{\"userId\":7,\"userName\":\"gold-girl\",\"firstName\":\"Zlata\",\"lastName\":\"Maximochkina\",\"email\":\"zlata_max@gmail.com\",\"title\":{\"titleId\":5,\"titleName\":\"Network Administrator\"},\"department\":{\"departmentId\":1,\"departmentName\":\"Department1\"},\"roles\":[{\"roleId\":1,\"roleName\":\"USER\"}]}," +
                                "{\"userId\":8,\"userName\":\"lihach\",\"firstName\":\"Daria\",\"lastName\":\"Lihacheva\",\"email\":\"lihach@tut.by\",\"title\":{\"titleId\":1,\"titleName\":\"Software Developer\"},\"department\":{\"departmentId\":1,\"departmentName\":\"Department1\"},\"roles\":[{\"roleId\":1,\"roleName\":\"USER\"}]}," +
                                "{\"userId\":9,\"userName\":\"natasha\",\"firstName\":\"Natalia\",\"lastName\":\"Romanova\",\"email\":\"nataly@outlook.com\",\"title\":{\"titleId\":3,\"titleName\":\"Data Architect\"},\"department\":{\"departmentId\":1,\"departmentName\":\"Department1\"},\"roles\":[{\"roleId\":1,\"roleName\":\"USER\"}]}," +
                                "{\"userId\":10,\"userName\":\"rabbit\",\"firstName\":\"Anastasia\",\"lastName\":\"Safonova\",\"email\":\"nastya@gmail.com\",\"title\":{\"titleId\":4,\"titleName\":\"Software Engineer\"},\"department\":{\"departmentId\":1,\"departmentName\":\"Department1\"},\"roles\":[{\"roleId\":2,\"roleName\":\"ADMIN\"}]}")));
    }

    @Test
    public void getUserTest() throws Exception {
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

        mockMvc.perform(get("/manager/users/{id}",1).header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("" +
                        "{" +
                        "\"userId\":1," +
                        "\"userName\":\"123user123\"," +
                        "\"firstName\":\"Ivan\"," +
                        "\"lastName\":\"Ivanov\"," +
                        "\"email\":\"vania@tut.by\"," +
                        "\"title\":{\"titleId\":1,\"titleName\":\"Software Developer\"}," +
                        "\"department\":{\"departmentId\":1,\"departmentName\":\"Department1\"}," +
                        "\"roles\":[{\"roleId\":1,\"roleName\":\"USER\"}]" +
                        "}")));
    }

    @Test
    public void postUserTest() throws Exception {
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

        requestJson = "" +
                "{\n" +
                "  \"userId\":11,\n" +
                "  \"userName\":\"123user123\",\n" +
                "  \"firstName\":\"Ivan\",\n" +
                "  \"lastName\":\"Ivanov\",\n" +
                "  \"email\":\"vania@tut.by\",\n" +
                "  \"password\":\"password\",\n" +
                "  \"titleId\":1,\n" +
                "  \"departmentId\":1,\n" +
                "  \"roleId\":[]\n" +
                "}";

        mockMvc.perform(post("/manager/users")
                .contentType(APPLICATION_JSON_UTF8).content(requestJson)
                .header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    @Test
    public void testUpdateUser() throws Exception {
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

        requestJson = "" +
                "{\n" +
                "  \"userId\":1,\n" +
                "  \"userName\":\"123user123\",\n" +
                "  \"firstName\":\"Ivan\",\n" +
                "  \"lastName\":\"Ivanov\",\n" +
                "  \"email\":\"vania@tut.by\",\n" +
                "  \"password\":\"password\",\n" +
                "  \"titleId\":1,\n  \"departmentId\":1,\n" +
                "  \"roleId\":[1]\n" +
                "}";

        mockMvc.perform(put("/manager/users/{id}",1)
                .contentType(APPLICATION_JSON_UTF8).content(requestJson)
                .header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "{" +
                                "\"userId\":1," +
                                "\"userName\":\"123user123\"," +
                                "\"firstName\":\"Ivan\"," +
                                "\"lastName\":\"Ivanov\"," +
                                "\"email\":\"vania@tut.by\"," +
                                "\"title\":{\"titleId\":1,\"titleName\":\"Software Developer\"}," +
                                "\"department\":{\"departmentId\":1,\"departmentName\":\"Department1\"}," +
                                "\"roles\":[{\"roleId\":1,\"roleName\":\"USER\"}]" +
                                "}")));
    }

    @Test
    public void testDeleteUser() throws Exception {
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

        mockMvc.perform(delete("/manager/users/{id}",1).header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/manager/users/{id}",1).header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("")));

        mockMvc.perform(get("/manager/users").header("Authorization","Bearer_"+token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));

        }

}
