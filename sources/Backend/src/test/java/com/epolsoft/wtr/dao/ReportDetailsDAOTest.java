package com.epolsoft.wtr.dao;
import com.epolsoft.wtr.model.*;
import com.epolsoft.wtr.model.Enums.Status;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import static com.epolsoft.wtr.controller.ReportDetailsController.setDateOneDay;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value={"/create-reportDetails-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value={"/create-reportDetails-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ReportDetailsDAOTest {

    @Autowired
    private ReportDetailsDAO reportDetailsDAO;

    @Autowired
    private LocationDAO locationDAO;

    @Autowired
    private FactorDAO factorDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ReportDetails createNewReportDetails() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("yyyy-MM-dd");
        Date date = dateFormat.parse("2019-12-02");
        ReportDetails reportDetails = new ReportDetails(
                11,
                date,
                Status.REGISTERED,
                factorDAO.findById(2).get(),
                locationDAO.findById(2).get(),
                1.0,
                1.0,
                null,
                null,
                null,
                null,
                "comment",
                new HashSet<User>());
        return reportDetails;
    }


    @Test
    public void createReportDetailsTest() throws ParseException {
        ReportDetails reportDetails = createNewReportDetails();

        ReportDetails reportDetails1=reportDetailsDAO.save(reportDetails);

        Assert.assertEquals(reportDetails.getReportDetailsId(),reportDetails1.getReportDetailsId());
        Assert.assertEquals(reportDetails.getStatus(),reportDetails1.getStatus());
        Assert.assertEquals(reportDetails.getReportDetailsDate(),reportDetails1.getReportDetailsDate());
        Assert.assertEquals(reportDetails.getHours(),reportDetails1.getHours());
        Assert.assertEquals(reportDetails.getWorkUnits(),reportDetails1.getWorkUnits());
        Assert.assertEquals(reportDetails.getDetailedTask(),reportDetails1.getDetailedTask());
        Assert.assertEquals(reportDetails.getTask(),reportDetails1.getTask());
        Assert.assertEquals(reportDetails.getFeature(),reportDetails1.getFeature());
        Assert.assertEquals(reportDetails.getProject(),reportDetails1.getProject());
        Assert.assertEquals(reportDetails.getComments(),reportDetails1.getComments());

        reportDetails1 = jdbcTemplate.queryForObject(
                "SELECT * FROM wtrtest.ReportDetails where reportDetailsId = ? ",
                new Object[] { reportDetails.getReportDetailsId() },
                new BeanPropertyRowMapper<ReportDetails>(ReportDetails.class)
        );

        Assert.assertEquals(reportDetails.getReportDetailsId(),reportDetails1.getReportDetailsId());
        Assert.assertEquals(reportDetails.getStatus(),reportDetails1.getStatus());
        Assert.assertEquals(reportDetails.getHours(),reportDetails1.getHours());
        Assert.assertEquals(reportDetails.getWorkUnits(),reportDetails1.getWorkUnits());
        Assert.assertEquals(reportDetails.getDetailedTask(),reportDetails1.getDetailedTask());
        Assert.assertEquals(reportDetails.getTask(),reportDetails1.getTask());
        Assert.assertEquals(reportDetails.getFeature(),reportDetails1.getFeature());
        Assert.assertEquals(reportDetails.getProject(),reportDetails1.getProject());
        Assert.assertEquals(reportDetails.getComments(),reportDetails1.getComments());
    }

    @Test
    public void createListOfReportDetailsTest(){
        List <ReportDetails> reportDetailsList = new ArrayList<>();
        reportDetailsList.add(new ReportDetails(
                    11,
                    new Date(),
                    Status.REGISTERED,
                    factorDAO.findById(1).get(),
                    locationDAO.findById(2).get(),
                    5.0,
                    8.0,
                    null,
                    null,
                    null,
                    null,
                    "First",
                    new HashSet<User>())
        );
        reportDetailsList.add(new ReportDetails(
                    12,
                    new Date(),
                    Status.REJECTED,
                    factorDAO.findById(1).get(),
                    locationDAO.findById(1).get(),
                    9.0,
                    7.0,
                    null,
                    null,
                    null,
                    null,
                    "Second",
                    new HashSet<User>())
        );
        reportDetailsList.add(new ReportDetails(
                    13,
                    new Date(),
                    Status.PRIVATE,
                    factorDAO.findById(2).get(),
                    locationDAO.findById(1).get(),
                    2.0,
                    5.0,
                    null,
                    null,
                    null,
                    null,
                    "Third",
                    new HashSet<User>())
        );

        List<ReportDetails> reportDetailsList1 = reportDetailsDAO.saveAll(reportDetailsList);
        for(int i = 0; i < reportDetailsList.size(); i++){
            Assert.assertEquals(reportDetailsList.get(i).getReportDetailsId(), reportDetailsList1.get(i).getReportDetailsId());
            Assert.assertEquals(reportDetailsList.get(i).getComments(), reportDetailsList1.get(i).getComments());
            Assert.assertEquals(reportDetailsList.get(i).getStatus(), reportDetailsList1.get(i).getStatus());
            Assert.assertEquals(reportDetailsList.get(i).getHours(), reportDetailsList1.get(i).getHours());
            Assert.assertEquals(reportDetailsList.get(i).getWorkUnits(), reportDetailsList1.get(i).getWorkUnits());
        }
    }

    @Test
    public void findAllReportDetailsTest(){
        Iterable <ReportDetails> reportDetails = reportDetailsDAO.findAll();
        List<ReportDetails> reportDetailsList = new ArrayList();
        reportDetails.forEach(reportDetailsList :: add);
        Assert.assertEquals(10, reportDetailsList.size());

        for(int i = 0; i <reportDetailsList.size(); i++){
            Assert.assertEquals((i + 1), reportDetailsList.get(i).getReportDetailsId().intValue());
        }
    }
    @Test
    public void findByReportDetailsIdTest(){
        Optional<ReportDetails> reportDetails = reportDetailsDAO.findById(2);
        if (reportDetails.isPresent()){
            ReportDetails reportDetailsTest = reportDetails.get();
            Assert.assertEquals((Integer)2,reportDetailsTest.getReportDetailsId());
            Assert.assertTrue(reportDetailsTest.getReportDetailsId().equals(2));
            Assert.assertFalse(reportDetailsTest.getStatus().equals(Status.PRIVATE));
        }
    }
    @Test
    public void deleteReportDetailsTest(){
        ReportDetails reportDetails = reportDetailsDAO.findById(3).get();
        reportDetailsDAO.delete(reportDetails);
        Assert.assertFalse(reportDetailsDAO.findById(3).isPresent());
    }
    @Test
    public void deleteAllReportDetailsTest(){
        List<ReportDetails> reportDetailsList = reportDetailsDAO.findAll();
        reportDetailsDAO.deleteAll();
        Assert.assertTrue(reportDetailsDAO.findAll().isEmpty());
        Assert.assertFalse(reportDetailsList.get(3).getReportDetailsId().equals(3));
    }

    @Test
    public void getReportDetailsByDateTest() throws ParseException {
        Date date = setDateOneDay("2019-12-02");
        List<ReportDetails> reportDetailsList = reportDetailsDAO.findAllByReportDetailsDate(date);
        Assert.assertEquals(3,reportDetailsDAO.findAllByReportDetailsDate(date).size());
    }
    @Test
    public void getReportDetailsBetweenOfDatesTest() throws ParseException {
        Date dateStart = setDateOneDay("2019-12-02");
        Date dateEnd = setDateOneDay("2019-12-03");
        List<ReportDetails> reportDetailsList = reportDetailsDAO.findAllByReportDetailsDateBetween(dateStart,dateEnd);
        Assert.assertEquals(6,reportDetailsDAO.findAllByReportDetailsDateBetween(dateStart,dateEnd).size());
        Assert.assertFalse(reportDetailsList.size() == 5);
    }

    @Test
    public void findAllReportsByUserIdAndMonthTest() throws ParseException {
        Assert.assertEquals(10,reportDetailsDAO.findAllReportsByUserIdAndMonth(1,12,2019).size());
    }

    @Test
    public void findAllReportsByFilter(){
        List<ReportDetails> reportDetailsList = reportDetailsDAO.findAllReportsByFilter(1);
        List<ReportDetails> reportDetailsList1 = new ArrayList();
        reportDetailsList1 = jdbcTemplate.query(
                "SELECT a.* FROM wtrtest.ReportDetails a LEFT JOIN wtrtest.Report b ON a.reportDetailsId=b.reportDetailsId WHERE b.userId=1",
                new BeanPropertyRowMapper<ReportDetails>(ReportDetails.class)
        );
        Assert.assertEquals(reportDetailsList.size(),reportDetailsList1.size());
        for(int i=0;i<reportDetailsList.size();i++)
            Assert.assertEquals(reportDetailsList.get(i).getReportDetailsId(),reportDetailsList1.get(i).getReportDetailsId());
    }

    @Test
    public void findAllReportsByUserIdAndDateTest() throws ParseException {
        Assert.assertEquals(1,reportDetailsDAO.findAllReportsByUserIdAndDate(1,"2019-12-01").size());
    }

}
