package com.epolsoft.wtr.service;

import com.epolsoft.wtr.dao.DepartmentDAO;
import com.epolsoft.wtr.dao.RoleDAO;
import com.epolsoft.wtr.dao.TitleDAO;
import com.epolsoft.wtr.dao.UserDAO;
import com.epolsoft.wtr.model.*;
import com.epolsoft.wtr.model.dto.UserDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserDAO userDAO;

    @MockBean
    private RoleDAO roleDAO;

    @MockBean
    private TitleDAO titleDAO;

    @MockBean
    private DepartmentDAO departmentDAO;

    @Test
    public void createUserTest() {
        UserDTO userDTO = new UserDTO(
                1,
                "123user123",
                "Ivan",
                "Ivanov",
                "vania@tut.by",
                "password",
                1,
                1,
                new HashSet<Integer>(Arrays.asList(1)));

        Mockito.doReturn(Optional.of(new Title()))
                .when(titleDAO)
                .findById(Mockito.any(Integer.class));

        Mockito.doReturn(Optional.of(new Department()))
                .when(departmentDAO)
                .findById(Mockito.any(Integer.class));

        Mockito.doReturn(Optional.of(new Role(1,"User",new HashSet<User>())))
                .when(roleDAO)
                .findById(1);

        userService.createUser(userDTO);

        Mockito.verify(userDAO, Mockito.times(1)).save(Mockito.any(User.class));
        Mockito.verify(roleDAO, Mockito.times(1)).save(Mockito.any(Role.class));

    }

    @Test
    public void updateUserTest() {
        UserDTO userDTO = new UserDTO(
                1,
                "123user123",
                "Ivan",
                "Ivanov",
                "vania@tut.by",
                "password",
                1,
                1,
                new HashSet<Integer>(Arrays.asList(1)));

        Mockito.doReturn(Optional.of(new User(null,null,null,null,null,null,null,null,null,null)))
                .when(userDAO)
                .findById(1);

        Mockito.doReturn(Optional.of(new Title()))
                .when(titleDAO)
                .findById(Mockito.any(Integer.class));

        Mockito.doReturn(Optional.of(new Department()))
                .when(departmentDAO)
                .findById(Mockito.any(Integer.class));

        Mockito.doReturn(Optional.of(new Role(1,"User",new HashSet<User>())))
                .when(roleDAO)
                .findById(1);

        userService.updateUser(userDTO);

        Mockito.verify(userDAO, Mockito.times(1)).findById(1);
        Mockito.verify(userDAO, Mockito.times(1)).save(Mockito.any(User.class));
    }

    @Test
    public void getAllUsersTest() {
        User user = new User(
                1,
                "123user123",
                "Ivan",
                "Ivanov",
                "vania@tut.by",
                "password",
                new Title(),
                new Department(),
                new HashSet<Role>(),
                new HashSet<ReportDetails>());

        List<User> users = new ArrayList<User>();
        users.add(user);

        Mockito.doReturn(users).when(userDAO).findAll();

        Assert.assertNotNull(userService.getAllUsers());
        Mockito.verify(userDAO, Mockito.times(1)).findAll();
    }

    @Test
    public void getUserById() {
        User user = new User(
                1,
                "123user123",
                "Ivan",
                "Ivanov",
                "vania@tut.by",
                "",
                new Title(),
                new Department(),
                new HashSet<Role>(),
                new HashSet<ReportDetails>());

        Mockito.doReturn(Optional.of(user))
                .when(userDAO)
                .findById(1);

        Assert.assertEquals(user, userService.getUserById(user.getUserId()));
    }

    @Test
    public void getUserByName() {
        User user = new User(
                1,
                "123user123",
                "Ivan",
                "Ivanov",
                "vania@tut.by",
                "",
                new Title(),
                new Department(),
                new HashSet<Role>(),
                new HashSet<ReportDetails>());

        Mockito.doReturn(user)
                .when(userDAO)
                .findByUsername(user.getUserName());

        Assert.assertEquals(user, userService.getUserByName(user.getUserName()));
    }

    @Test
    public void getUsersByFilter() {
        User user = new User(
                1,
                "123user123",
                "Ivan",
                "Ivanov",
                "vania@tut.by",
                "",
                new Title(),
                new Department(),
                new HashSet<Role>(),
                new HashSet<ReportDetails>());

        Mockito.doReturn(new ArrayList<User>(Arrays.asList(user)))
                .when(userDAO)
                .filterByFirstNameAndLastName("","");

        List<User> res=userService.getUsersByFilter("","");
        Assert.assertEquals(1,res.size());
        Assert.assertEquals(user,res.get(0));
    }

    @Test
    public void deleteUserTest() {
        User user = new User(
                1,
                "123user123",
                "Ivan",
                "Ivanov",
                "vania@tut.by",
                "",
                new Title(),
                new Department(),
                new HashSet<Role>(),
                new HashSet<ReportDetails>());

        Mockito.doReturn(Optional.of(user))
                .when(userDAO)
                .findById(1);

        userService.deleteUser(user.getUserId());

        Mockito.verify(userDAO, Mockito.times(1)).delete(Mockito.any(User.class));
    }
}
