package com.epolsoft.wtr.dao;

import com.epolsoft.wtr.model.Role;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value={"/create-role-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value={"/create-role-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class RoleDAOTest {

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void createRole()
    {
        Role role=new Role(4,"DIRECTOR",new HashSet<>());

        Role roleCreate = roleDAO.save(role);
        Assert.assertTrue(role.getRoleId().equals(roleCreate.getRoleId()));
        Assert.assertTrue(role.getRoleName().equals(roleCreate.getRoleName()));

        roleCreate = jdbcTemplate.queryForObject(
                "SELECT * FROM wtrtest.Role where roleId = ? ",
                new Object[] {role.getRoleId()},
                new BeanPropertyRowMapper<Role>(Role.class)
        );

        Assert.assertTrue(role.getRoleId().equals(roleCreate.getRoleId()));
        Assert.assertTrue(role.getRoleName().equals(roleCreate.getRoleName()));
    }

    @Test
    public void findByIdTest()
    {
        Role role = new Role(1, "USER",new HashSet<>());

        Optional<Role> roleFind = roleDAO.findById(1);
        Assert.assertTrue(Optional.of(roleFind).isPresent());
        Assert.assertTrue(role.getRoleId().equals(roleFind.get().getRoleId()));
        Assert.assertTrue(role.getRoleName().equals(roleFind.get().getRoleName()));

        Role role1 = jdbcTemplate.queryForObject(
                "SELECT * FROM wtrtest.Role where roleId = ? ",
                new Object[] {role.getRoleId()},
                new BeanPropertyRowMapper<Role>(Role.class)
        );

        Assert.assertTrue(role.getRoleId().equals(role1.getRoleId()));
        Assert.assertTrue(role.getRoleName().equals(role1.getRoleName()));
    }

    @Test
    public void findByNameTest()
    {
        Role role = new Role(1, "USER",new HashSet<>());

        Role roleFind = roleDAO.findByRoleName("USER");
        Assert.assertTrue(Optional.of(roleFind).isPresent());
        Assert.assertTrue(role.getRoleId().equals(roleFind.getRoleId()));
        Assert.assertTrue(role.getRoleName().equals(roleFind.getRoleName()));

        Role role1 = jdbcTemplate.queryForObject(
                "SELECT * FROM wtrtest.Role where roleName = ? ",
                new Object[] {role.getRoleName()},
                new BeanPropertyRowMapper<Role>(Role.class)
        );

        Assert.assertTrue(role.getRoleId().equals(role1.getRoleId()));
        Assert.assertTrue(role.getRoleName().equals(role1.getRoleName()));
    }

    @Test
    public void findAllTest()
    {
        Iterable <Role> allroles = roleDAO.findAll();
        List<Role> roleList=new ArrayList();
        allroles.forEach(roleList::add);
        Assert.assertEquals(3,roleList.size());

        List<Role> roleList1=new ArrayList();
        roleList1= jdbcTemplate.query(
                "SELECT * FROM wtrtest.Role",
                new BeanPropertyRowMapper<Role>(Role.class)
        );

        for (int i=0;i<roleList.size();i++)
        {
            Assert.assertTrue(roleList.get(i).getRoleId().equals(roleList1.get(i).getRoleId()));
            Assert.assertTrue(roleList.get(i).getRoleName().equals(roleList1.get(i).getRoleName()));
        }
    }

    @Test
    public void deleteTest()
    {
        Role role = new Role(1,"USER", null);

        roleDAO.delete(role);

        List<Role> roleList=new ArrayList();
        roleList= jdbcTemplate.query(
                "SELECT * FROM wtrtest.Role",
                new BeanPropertyRowMapper<Role>(Role.class)
        );

        Assert.assertFalse(roleList.contains(role));
        Assert.assertEquals(2, roleList.size());
    }
}
