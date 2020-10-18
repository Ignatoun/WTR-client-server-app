package com.epolsoft.wtr.dao;

import com.epolsoft.wtr.model.Department;
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
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value={"/create-department-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value={"/create-department-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class DepartmentDAOTest {

    @Autowired
    private DepartmentDAO departmentDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void createDepartmentTest()
    {
        Department department=new Department(6,"Department1",null);
        Department department1=new Department();
        Department department2=new Department();

        department1=departmentDAO.save(department);

        Assert.assertTrue(department.equals(department1));

        department2 = jdbcTemplate.queryForObject(
                "SELECT * FROM wtrtest.Department where departmentId = ? ",
                new Object[] { department.getDepartmentId() },
                new BeanPropertyRowMapper<Department>(Department.class)
        );

        Assert.assertTrue(department.equals(department2));
    }

    @Test
    public void findAllDepartmentTest()
    {
        Iterable <Department> alldepartment = departmentDAO.findAll();
        List<Department> departmentList=new ArrayList();
        alldepartment.forEach(departmentList::add);

        Assert.assertEquals(5,departmentList.size());

        List<Department> departmentList1=new ArrayList();
        departmentList1= jdbcTemplate.query(
                "SELECT * FROM wtrtest.Department",
                new BeanPropertyRowMapper<Department>(Department.class)
        );

        for (int i=0;i<departmentList.size();i++)
        {
            Assert.assertTrue(departmentList.get(i).getDepartmentId().equals(departmentList1.get(i).getDepartmentId()));
            Assert.assertTrue(departmentList.get(i).getDepartmentName().equals(departmentList1.get(i).getDepartmentName()));
        }
    }

    @Test
    public void findByIdTest()
    {
        Department department=new Department(1,"Department1",null);
        Department department1=new Department();

        department1 = jdbcTemplate.queryForObject(
                "SELECT * FROM wtrtest.Department where departmentId = ? ",
                new Object[] { department.getDepartmentId() },
                new BeanPropertyRowMapper<Department>(Department.class)
        );

        Optional<Department> department3 = departmentDAO.findById(department.getDepartmentId());

        Assert.assertTrue(department3.isPresent());
        Assert.assertTrue(department1.getDepartmentId().equals(department3.get().getDepartmentId()));
        Assert.assertTrue(department1.getDepartmentName().equals(department3.get().getDepartmentName()));
        Assert.assertFalse(departmentDAO.findById(99).isPresent());
    }

    @Test
    public void deleteTest()
    {
        Department department= new Department(2,"Department2",null);
        List <Department> departmentList=new ArrayList();
        AtomicBoolean flag= new AtomicBoolean(false);

        departmentDAO.delete(department);

        departmentList = jdbcTemplate.query(
                "SELECT * FROM wtrtest.Department",
                new BeanPropertyRowMapper<Department>(Department.class)
        );

        departmentList.forEach(dep->
        {
            if(dep.equals(department))
            {
                flag.set(true);
            }
        });

        Assert.assertFalse(flag.get());
    }

    @Test
    public void deleteAll()
    {
        Integer count=5;

        departmentDAO.deleteAll();

        count= jdbcTemplate.queryForObject("SELECT COUNT(*) FROM wtrtest.Department",Integer.TYPE);

        Assert.assertEquals(0,departmentDAO.count());
    }

}