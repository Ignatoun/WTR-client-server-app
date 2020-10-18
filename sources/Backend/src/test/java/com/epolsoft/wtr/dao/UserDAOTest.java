package com.epolsoft.wtr.dao;

import com.epolsoft.wtr.model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value={"/create-user-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value={"/create-user-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserDAOTest {

    @Autowired
    private UserDAO userDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void createUserTest() {
        User user = new User(
                11,
                "123user123",
                "Ivan",
                "Ivanov",
                "vania@tut.by",
                "$2a$04$iuSjuFKUitIy9vTJ8uRl6OkJ4MWaSboeQzpb1slHQQl.J5WCjEara",
                null,
                null,
                new HashSet<Role>(),
                new HashSet<ReportDetails>());
        User user1 = new User();
        User user2 = new User();

        user1 = userDao.save(user);

        Assert.assertEquals(user.getUserId(),user1.getUserId());
        Assert.assertEquals(user.getUserName(),user1.getUserName());
        Assert.assertEquals(user.getFirstName(),user1.getFirstName());
        Assert.assertEquals(user.getLastName(),user1.getLastName());
        Assert.assertEquals(user.getEmail(),user1.getEmail());
        Assert.assertEquals(user.getTitle(),user1.getTitle());
        Assert.assertEquals(user.getDepartment(),user1.getDepartment());

        user2 = jdbcTemplate.queryForObject(
                "SELECT * FROM wtrtest.User where userId = ? ",
                new Object[] { user.getUserId() },
                new BeanPropertyRowMapper<User>(User.class)
        );

        Assert.assertEquals(user.getUserId(),user2.getUserId());
        Assert.assertEquals(user.getUserName(),user2.getUserName());
        Assert.assertEquals(user.getFirstName(),user2.getFirstName());
        Assert.assertEquals(user.getLastName(),user2.getLastName());
        Assert.assertEquals(user.getEmail(),user2.getEmail());
        Assert.assertEquals(user.getTitle(),user2.getTitle());
        Assert.assertEquals(user.getDepartment(),user2.getDepartment());
    }

    @Test
    public void findAllUsersTest() {
        Iterable<User> users = userDao.findAll();
        List<User> usersList = new ArrayList<User>();
        users.forEach(usersList::add);

        Assert.assertEquals(10,usersList.size());

        List<User> usersList1 = new ArrayList();
        usersList1 = jdbcTemplate.query(
                "SELECT * FROM wtrtest.User",
                new BeanPropertyRowMapper<User>(User.class)
        );

        for (int i=0; i<usersList.size(); i++) {
            Assert.assertTrue(usersList.get(i).getUserId().equals(usersList1.get(i).getUserId()));
            Assert.assertTrue(usersList.get(i).getUserName().equals(usersList1.get(i).getUserName()));
            Assert.assertTrue(usersList.get(i).getFirstName().equals(usersList1.get(i).getFirstName()));
            Assert.assertTrue(usersList.get(i).getLastName().equals(usersList1.get(i).getLastName()));
            Assert.assertTrue(usersList.get(i).getEmail().equals(usersList1.get(i).getEmail()));
        }
    }

    @Test
    public void findUserByIdTest() {
        User user = new User();

        user = jdbcTemplate.queryForObject(
                "SELECT * FROM wtrtest.User where userId = ? ",
                new Object[] { 1 },
                new BeanPropertyRowMapper<User>(User.class)
        );

        Optional<User> user2 = userDao.findById(1);

        Assert.assertTrue(user2.isPresent());
        Assert.assertTrue(user.getUserId().equals(user2.get().getUserId()));
        Assert.assertTrue(user.getUserName().equals(user2.get().getUserName()));
        Assert.assertTrue(user.getFirstName().equals(user2.get().getFirstName()));
        Assert.assertTrue(user.getLastName().equals(user2.get().getLastName()));
        Assert.assertTrue(user.getEmail().equals(user2.get().getEmail()));
        Assert.assertFalse(userDao.findById(99).isPresent());
    }

    @Test
    public void findByUsernameTest() {
        User user = new User();

        user = jdbcTemplate.queryForObject(
                "SELECT * FROM wtrtest.User where userName = ? ",
                new Object[] { "123user123" },
                new BeanPropertyRowMapper<User>(User.class)
        );

        User user2 = userDao.findByUsername("123user123");

        Assert.assertTrue(user.getUserId().equals(user2.getUserId()));
        Assert.assertTrue(user.getUserName().equals(user2.getUserName()));
        Assert.assertTrue(user.getFirstName().equals(user2.getFirstName()));
        Assert.assertTrue(user.getLastName().equals(user2.getLastName()));
        Assert.assertTrue(user.getEmail().equals(user2.getEmail()));
    }

    @Test
    public void filterByFirstNameAndLastNameTest() {
        List<User> userList = userDao.filterByFirstNameAndLastName("Ivan","Ivanov");

        Assert.assertEquals(1,userList.size());
        Assert.assertTrue(userList.get(0).getUserId().equals(1));
        Assert.assertTrue(userList.get(0).getUserName().equals("123user123"));
        Assert.assertTrue(userList.get(0).getFirstName().equals("Ivan"));
        Assert.assertTrue(userList.get(0).getLastName().equals("Ivanov"));
        Assert.assertTrue(userList.get(0).getEmail().equals("vania@tut.by"));
    }

    @Test
    public void deleteUserTest() {
        User user = new User(
                1,
                "123user123",
                "Ivan",
                "Ivanov",
                "vania@tut.by",
                "$2a$04$iuSjuFKUitIy9vTJ8uRl6OkJ4MWaSboeQzpb1slHQQl.J5WCjEara",
                null,
                null,
                new HashSet<Role>(),
                new HashSet<ReportDetails>());
        List <User> userList = new ArrayList();
        AtomicBoolean flag = new AtomicBoolean(false);

        userDao.delete(user);

        userList = jdbcTemplate.query(
                "SELECT * FROM wtrtest.User",
                new BeanPropertyRowMapper<User>(User.class)
        );

        userList.forEach(use->
        {
            if(use.getUserId()==user.getUserId())
            {
                flag.set(true);
            }
        });

        Assert.assertFalse(flag.get());
    }

    @Test
    public void deleteAll() {
        Integer count = 10;

        userDao.deleteAll();

        count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM wtrtest.User",Integer.TYPE);

        Assert.assertEquals(0, userDao.count());
    }
}
