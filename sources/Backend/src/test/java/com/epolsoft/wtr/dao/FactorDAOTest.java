package com.epolsoft.wtr.dao;

import com.epolsoft.wtr.model.Factor;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value={"/create-factor-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value={"/create-factor-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class FactorDAOTest {
    @Autowired
    private FactorDAO factorDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void createFactor()
    {
        Factor factor = new Factor(10, "Sick",null);

        Factor factorCreate=factorDAO.save(factor);
        Assert.assertTrue(factor.equals(factorCreate));

        factorCreate = jdbcTemplate.queryForObject(
                "SELECT * FROM wtrtest.Factor where factorId = ? ",
                new Object[] {factor.getFactorId()},
                new BeanPropertyRowMapper<Factor>(Factor.class)
        );

        Assert.assertTrue(factor.equals(factorCreate));
    }

    @Test
    public void findByIdTest()
    {
        Factor factor = new Factor(4,"Sick or Care Absence", null);

        Factor factorFind = factorDAO.findById(4).get();
        Assert.assertTrue(Optional.of(factorFind).isPresent());
        Assert.assertTrue(factor.getFactorId().equals(factorFind.getFactorId()));
        Assert.assertTrue(factor.getFactorName().equals(factorFind.getFactorName()));


        factorFind = jdbcTemplate.queryForObject(
                "SELECT * FROM wtrtest.Factor where factorId = ? ",
                new Object[] {factor.getFactorId()},
                new BeanPropertyRowMapper<Factor>(Factor.class)
        );

        Assert.assertTrue(Optional.of(factorFind).isPresent());
        Assert.assertTrue(factor.getFactorId().equals(factorFind.getFactorId()));
        Assert.assertTrue(factor.getFactorName().equals(factorFind.getFactorName()));
    }

    @Test
    public void findAllTest()
    {
        Iterable <Factor> allfactors = factorDAO.findAll();
        List <Factor> factorList=new ArrayList();
        allfactors.forEach(factorList::add);
        Assert.assertEquals(9,factorList.size());

        List<Factor> factorList1=new ArrayList();
        factorList1= jdbcTemplate.query(
                "SELECT * FROM wtrtest.Factor",
                new BeanPropertyRowMapper<Factor>(Factor.class)
        );

        for (int i=0;i<factorList.size();i++)
        {
            Assert.assertTrue(factorList.get(i).getFactorId().equals(factorList1.get(i).getFactorId()));
            Assert.assertTrue(factorList.get(i).getFactorName().equals(factorList1.get(i).getFactorName()));
        }
    }

    @Test
    public void deleteTest()
    {
        Factor factor= new Factor(4, "Sick or Care Absence", null);
        factorDAO.delete(factor);

        List<Factor> factorList=new ArrayList();
        factorList= jdbcTemplate.query(
                "SELECT * FROM wtrtest.Factor",
                new BeanPropertyRowMapper<Factor>(Factor.class)
        );

            Assert.assertFalse(factorList.contains(factor));
            Assert.assertEquals(8, factorList.size());
    }

}
