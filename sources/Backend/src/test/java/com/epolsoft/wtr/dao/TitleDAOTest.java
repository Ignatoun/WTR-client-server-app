package com.epolsoft.wtr.dao;

import com.epolsoft.wtr.model.Title;
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
@Sql(value={"/create-title-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value={"/create-title-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TitleDAOTest {

    @Autowired
    private TitleDAO titleDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void createTitleTest()
    {
        Title title=new Title(6,"Title", null);
        Title title1=new Title();
        Title title2=new Title();

        title1=titleDAO.save(title);

        Assert.assertTrue(title.getTitleId().equals(title1.getTitleId()));
        Assert.assertTrue(title.getTitleName().equals(title1.getTitleName()));

        title2 = jdbcTemplate.queryForObject(
                "SELECT * FROM wtrtest.Title where titleId = ? ",
                new Object[] { title.getTitleId() },
                new BeanPropertyRowMapper<Title>(Title.class)
        );

        Assert.assertTrue(title.getTitleId().equals(title1.getTitleId()));
        Assert.assertTrue(title.getTitleName().equals(title1.getTitleName()));
    }

    @Test
    public void findAllTitleTest()
    {

        Iterable <Title> alltitle = titleDAO.findAll();
        List <Title> titleList=new ArrayList();
        alltitle.forEach(titleList::add);

        Assert.assertEquals(5,titleList.size());

        List<Title> titleList1=new ArrayList();
        titleList1= jdbcTemplate.query(
                "SELECT * FROM wtrtest.Title",
                new BeanPropertyRowMapper<Title>(Title.class)
        );

        for (int i=0;i<titleList.size();i++)
        {
            Assert.assertTrue(titleList.get(i).getTitleId().equals(titleList1.get(i).getTitleId()));
            Assert.assertTrue(titleList.get(i).getTitleName().equals(titleList1.get(i).getTitleName()));
        }
    }

    @Test
    public void findByIdTest()
    {
        Title title=new Title(3,"Title3", null);
        Title title1=new Title();

        title1 = jdbcTemplate.queryForObject(
                "SELECT * FROM wtrtest.Title where titleId = ? ",
                new Object[] { title.getTitleId() },
                new BeanPropertyRowMapper<Title>(Title.class)
        );

        Optional<Title> title2 = titleDAO.findById(title.getTitleId());

        Assert.assertTrue(title2.isPresent());
        Assert.assertTrue(title.getTitleId().equals(title1.getTitleId()));
        Assert.assertTrue(title.getTitleName().equals(title1.getTitleName()));
        Assert.assertFalse(titleDAO.findById( 99).isPresent());
    }

    @Test
    public void deleteTest()
    {
        Title title= new Title(2,"Title2", null);
        List <Title> titleList=new ArrayList();
        AtomicBoolean flag= new AtomicBoolean(false);

        titleDAO.delete(title);

        titleList = jdbcTemplate.query(
                "SELECT * FROM wtrtest.Title",
                new BeanPropertyRowMapper<Title>(Title.class)
        );

        titleList.forEach(tl->
        {
            if(tl.equals(title))
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

        titleDAO.deleteAll();

        count= jdbcTemplate.queryForObject("SELECT COUNT(*) FROM wtrtest.Title",Integer.TYPE);

        Assert.assertEquals(0,titleDAO.count());
    }

}