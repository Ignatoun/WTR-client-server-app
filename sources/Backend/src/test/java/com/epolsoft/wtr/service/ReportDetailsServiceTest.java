package com.epolsoft.wtr.service;

import com.epolsoft.wtr.dao.*;
import com.epolsoft.wtr.model.*;
import com.epolsoft.wtr.model.Enums.Status;
import com.epolsoft.wtr.model.dto.ReportDetailsDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReportDetailsServiceTest {

    @Autowired
    private ReportDetailsService reportDetailsService;

    @MockBean
    private ReportDetailsDAO reportDetailsDAO;

    @MockBean
    private FactorDAO factorDAO;

    @MockBean
    private LocationDAO locationDAO;

    @MockBean
    private DetailedTaskDAO detailedTaskDAO;

    @MockBean
    private TaskDAO taskDAO;

    @MockBean
    private FeatureDAO featureDAO;

    @MockBean
    private ProjectDAO projectDAO;

    private static ReportDetails createNewReportDetails() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("yyyy-MM-dd");
        Date date = dateFormat.parse("2019-12-02");
        ReportDetails reportDetails = new ReportDetails(
                2,date,
                Status.REGISTERED,
                new Factor(1,null,new HashSet<ReportDetails>()),
                new Location(1,null,new HashSet<ReportDetails>()),
                1.0,
                1.0,
                new DetailedTask(1,null,new Tasks(),new HashSet<ReportDetails>()),
                new Tasks(1,null,new Feature(),new HashSet<ReportDetails>(),new HashSet<DetailedTask>()),
                new Feature(1,null,new Project(),new HashSet<ReportDetails>(),new HashSet<Tasks>()),
                new Project(1,null,null,null,new HashSet<ReportDetails>(),new HashSet<Feature>()),
                "comment",
                new HashSet<User>());
        reportDetails.setReportDetailsId(2);
        return reportDetails;
    }

    @Test
    public void testCreateReportDetails() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("yyyy-MM-dd");
        Date date = dateFormat.parse("2019-12-02");
        ReportDetailsDTO reportDetailsDTO= new ReportDetailsDTO(2,date,Status.REGISTERED,
                1,1,1.0,1.0,1,1,
                1,1,"comment",new HashSet<Integer>());

        Mockito.doReturn(Optional.of(new Factor()))
                .when(factorDAO)
                .findById(1);

        Mockito.doReturn(Optional.of(new Location()))
                .when(locationDAO)
                .findById(1);

        Mockito.doReturn(Optional.of(new DetailedTask()))
                .when(detailedTaskDAO)
                .findById(1);

        Mockito.doReturn(Optional.of(new Tasks()))
                .when(taskDAO)
                .findById(1);

        Mockito.doReturn(Optional.of(new Feature()))
                .when(featureDAO)
                .findById(1);

        Mockito.doReturn(Optional.of(new Project()))
                .when(projectDAO)
                .findById(1);

        reportDetailsService.createReportDetails(reportDetailsDTO);

        Mockito.verify(reportDetailsDAO, Mockito.times(1)).save(Mockito.any(ReportDetails.class));
    }

    @Test
    public void testCreateListOfReportDetails() throws ParseException {
        List <ReportDetailsDTO> reportDetailsDTOList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("yyyy-MM-dd");

        Date date = dateFormat.parse("2019-12-02");
        reportDetailsDTOList.add(new ReportDetailsDTO(2,date,Status.REGISTERED,
                1,1,1.0,1.0,1,1,
                1,1,"comment",new HashSet<Integer>()));

        date = dateFormat.parse("2018-11-05");
        reportDetailsDTOList.add(new ReportDetailsDTO(3,date,Status.PRIVATE,
                1,1,1.0,1.0,1,1,
                1,1,"comment",new HashSet<Integer>()));

        Mockito.doReturn(Optional.of(new Factor(1,null,new HashSet<ReportDetails>())))
                .when(factorDAO)
                .findById(1);

        Mockito.doReturn(Optional.of(new Location(1,null,new HashSet<ReportDetails>())))
                .when(locationDAO)
                .findById(1);

        Mockito.doReturn(Optional.of(new DetailedTask(1,null,new Tasks(),new HashSet<ReportDetails>())))
                .when(detailedTaskDAO)
                .findById(1);

        Mockito.doReturn(Optional.of(new Tasks(1,null,new Feature(),new HashSet<ReportDetails>(),new HashSet<DetailedTask>())))
                .when(taskDAO)
                .findById(1);

        Mockito.doReturn(Optional.of(new Feature(1,null,new Project(),new HashSet<ReportDetails>(),new HashSet<Tasks>())))
                .when(featureDAO)
                .findById(1);

        Mockito.doReturn(Optional.of(new Project(1,null,null,null,new HashSet<ReportDetails>(),new HashSet<Feature>())))
                .when(projectDAO)
                .findById(1);
        reportDetailsService.createListOfReportDetails(reportDetailsDTOList);
        Mockito.verify(reportDetailsDAO, Mockito.times(1)).saveAll(Mockito.any());
    }

    @Test
    public void testUpdateReportDetails() throws ParseException {
        ReportDetails reportDetails = createNewReportDetails();
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("yyyy-MM-dd");
        Date date = dateFormat.parse("2019-12-02");
        ReportDetailsDTO reportDetailsDTO= new ReportDetailsDTO(2,date,Status.REGISTERED,
                1,1,1.0,1.0,1,1,
                1,1,"comment",new HashSet<Integer>());

      Mockito.doReturn(Optional.of(reportDetails))
              .when(reportDetailsDAO)
              .findById(2);

      Mockito.doReturn(reportDetails)
              .when(reportDetailsDAO)
              .save(Mockito.any(ReportDetails.class));

        Mockito.doReturn(Optional.of(new Factor(1,null,new HashSet<ReportDetails>())))
                .when(factorDAO)
                .findById(1);

        Mockito.doReturn(Optional.of(new Location(1,null,new HashSet<ReportDetails>())))
                .when(locationDAO)
                .findById(1);

        Mockito.doReturn(Optional.of(new DetailedTask(1,null,new Tasks(),new HashSet<ReportDetails>())))
                .when(detailedTaskDAO)
                .findById(1);

        Mockito.doReturn(Optional.of(new Tasks(1,null,new Feature(),new HashSet<ReportDetails>(),new HashSet<DetailedTask>())))
                .when(taskDAO)
                .findById(1);

        Mockito.doReturn(Optional.of(new Feature(1,null,new Project(),new HashSet<ReportDetails>(),new HashSet<Tasks>())))
                .when(featureDAO)
                .findById(1);

        Mockito.doReturn(Optional.of(new Project(1,null,null,null,new HashSet<ReportDetails>(),new HashSet<Feature>())))
                .when(projectDAO)
                .findById(1);

        reportDetailsService.updateReportDetails(reportDetailsDTO);
        Mockito.verify(reportDetailsDAO, Mockito.times(1)).findById(Mockito.anyInt());
        Mockito.verify(reportDetailsDAO, Mockito.times(1)).save(Mockito.any(ReportDetails.class));
    }

    @Test
    public void testUpdateListOfReportDetails() throws ParseException {
        ReportDetails reportDetails = createNewReportDetails();
        List<ReportDetailsDTO> reportDetailsDTOList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("yyyy-MM-dd");
        Date date = dateFormat.parse("2019-12-02");
        reportDetailsDTOList.add(new ReportDetailsDTO(2,date,Status.REGISTERED,
                1,1,1.0,1.0,1,1,
                1,1,"comment",new HashSet<Integer>()));

        date = dateFormat.parse("2018-11-05");
        reportDetailsDTOList.add(new ReportDetailsDTO(3,date,Status.PRIVATE,
                1,1,1.0,1.0,1,1,
                1,1,"comment",new HashSet<Integer>()));

        Mockito.doReturn(Optional.of(reportDetails))
                .when(reportDetailsDAO)
                .findById(Mockito.anyInt());

        Mockito.doReturn(reportDetails)
                .when(reportDetailsDAO)
                .save(Mockito.any(ReportDetails.class));

        Mockito.doReturn(Optional.of(new Factor(1,null,new HashSet<ReportDetails>())))
                .when(factorDAO)
                .findById(1);

        Mockito.doReturn(Optional.of(new Location(1,null,new HashSet<ReportDetails>())))
                .when(locationDAO)
                .findById(1);

        Mockito.doReturn(Optional.of(new DetailedTask(1,null,new Tasks(),new HashSet<ReportDetails>())))
                .when(detailedTaskDAO)
                .findById(1);

        Mockito.doReturn(Optional.of(new Tasks(1,null,new Feature(),new HashSet<ReportDetails>(),new HashSet<DetailedTask>())))
                .when(taskDAO)
                .findById(1);

        Mockito.doReturn(Optional.of(new Feature(1,null,new Project(),new HashSet<ReportDetails>(),new HashSet<Tasks>())))
                .when(featureDAO)
                .findById(1);

        Mockito.doReturn(Optional.of(new Project(1,null,null,null,new HashSet<ReportDetails>(),new HashSet<Feature>())))
                .when(projectDAO)
                .findById(1);

        reportDetailsService.updateListOfReportDetails(reportDetailsDTOList);
        Mockito.verify(reportDetailsDAO, Mockito.times(2)).findById(Mockito.anyInt());
        Mockito.verify(reportDetailsDAO, Mockito.times(1)).saveAll(Mockito.any());
    }

    @Test
    public void testGetAllReportDetails() throws ParseException {
        List<ReportDetails> reportDetailsList = new ArrayList<>();
        ReportDetails reportDetails1 = createNewReportDetails();
        reportDetails1.setComments("com1");
        reportDetailsList.add(reportDetails1);
        ReportDetails reportDetails2 =createNewReportDetails();
        reportDetails1.setComments("com2");
        reportDetailsList.add(reportDetails2);

        Mockito.doReturn(reportDetailsList).
                when(reportDetailsDAO).
                findAll();

        Assert.assertEquals(reportDetailsList,reportDetailsService.getAllReportDetails());
        Mockito.verify(reportDetailsDAO, Mockito.times(1)).findAll();
        Assert.assertNotNull(reportDetailsService.getAllReportDetails());
    }

    @Test
    public void testGetReportDetailsById() throws ParseException {
        ReportDetails reportDetails = createNewReportDetails();

        Mockito.doReturn(Optional.of(reportDetails))
                .when(reportDetailsDAO)
                .findById(2);

        Assert.assertEquals(reportDetails, reportDetailsService.getReportDetailsById(reportDetails.getReportDetailsId()));
    }

    @Test
    public void testDeleteReportDetails() throws ParseException {
        ReportDetails reportDetails = createNewReportDetails();

        Mockito.doReturn(Optional.of(reportDetails))
                .when(reportDetailsDAO)
                .findById(2);

        reportDetailsService.deleteReportDetails(reportDetails.getReportDetailsId());

        Mockito.verify(reportDetailsDAO, Mockito.times(1)).deleteById(reportDetails.getReportDetailsId());
    }

    @Test
    public void testGetByDate() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("yyyy-MM-dd");
        Date date = dateFormat.parse("2019-12-02");

        List<ReportDetails> reportDetailsList = new ArrayList<>();

        ReportDetails reportDetails1 =createNewReportDetails();
        reportDetails1.setComments("com1");
        reportDetailsList.add(reportDetails1);
        ReportDetails reportDetails2 =createNewReportDetails();
        reportDetails1.setComments("com2");
        reportDetailsList.add(reportDetails2);

        Mockito.doReturn(reportDetailsList)
                .when(reportDetailsDAO)
                .findAllByReportDetailsDate(date);

        List<ReportDetails> reportDetailsList1 = reportDetailsService.getAllReportDetailsByDate(date);

        Mockito.verify(reportDetailsDAO, Mockito.times(1)).findAllByReportDetailsDate(date);
        Assert.assertNotNull(reportDetailsService.getAllReportDetailsByDate(date));
        Assert.assertEquals(reportDetailsList1,reportDetailsList);
    }

    @Test
    public void getAllReportDetailsByDateAndUserTest() throws ParseException {
        List<ReportDetails> reportDetailsList = new ArrayList<>();

        ReportDetails reportDetails1 =createNewReportDetails();
        reportDetails1.setComments("com1");
        reportDetailsList.add(reportDetails1);
        ReportDetails reportDetails2 =createNewReportDetails();
        reportDetails1.setComments("com2");
        reportDetailsList.add(reportDetails2);

        Mockito.doReturn(reportDetailsList)
                .when(reportDetailsDAO)
                .findAllReportsByUserIdAndDate(1,"2019-12-01");

        List<ReportDetails> reportDetailsList1 = reportDetailsService.getAllReportDetailsByDateAndUser("2019-12-01",1);

        Mockito.verify(reportDetailsDAO, Mockito.times(1)).findAllReportsByUserIdAndDate(1,"2019-12-01");
        Assert.assertEquals(reportDetailsList1,reportDetailsList);
    }

    @Test
    public void testGetReportDetailsBetweenOfDates() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("yyyy-MM-dd");
        Date dateStart = dateFormat.parse("2019-12-02");
        Date dateEnd = dateFormat.parse("2019-12-03");

        List<ReportDetails> reportDetailsList = new ArrayList<>();

        ReportDetails reportDetails1 =createNewReportDetails();
        reportDetails1.setComments("com1");
        reportDetailsList.add(reportDetails1);
        ReportDetails reportDetails2 = createNewReportDetails();
        reportDetails1.setComments("com2");
        reportDetails1.setReportDetailsDate(dateEnd);
        reportDetailsList.add(reportDetails2);

        Mockito.doReturn(reportDetailsList)
                .when(reportDetailsDAO)
                .findAllByReportDetailsDateBetween(dateStart,dateEnd);

        List<ReportDetails> reportDetailsList1 = reportDetailsService.getAllReportDetailsByBetweenOfDates(dateStart,dateEnd);

        Mockito.verify(reportDetailsDAO, Mockito.times(1))
                        .findAllByReportDetailsDateBetween(dateStart,dateEnd);
        Assert.assertNotNull(reportDetailsService.getAllReportDetailsByBetweenOfDates(dateStart,dateEnd));
        Assert.assertEquals(reportDetailsList1,reportDetailsList);
    }

    @Test
    public void testGetAllDatesWithoutReports() throws ParseException {
        List<ReportDetails> reportDetailsList = new ArrayList<>();

        ReportDetails reportDetails1 =createNewReportDetails();
        reportDetailsList.add(reportDetails1);

        Mockito.doReturn(reportDetailsList)
                .when(reportDetailsDAO)
                .findAllReportsByUserIdAndMonth(1,12,2019);

        List<Integer> datesWithoutReports = new ArrayList<>(Arrays.asList(
                1,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31));

        List<Integer> datesWithoutReports1 = reportDetailsService.getAllDatesWithoutReports(1,12,2019);
        Mockito.verify(reportDetailsDAO,Mockito.times(1)).findAllReportsByUserIdAndMonth(1,12,2019);
        Assert.assertEquals(datesWithoutReports,datesWithoutReports1);
    }

    @Test
    public void getReportsByFilterTest() throws ParseException {
        ReportDetails reportDetails = createNewReportDetails();
        List<ReportDetails> reportDetailsList = new ArrayList();
        reportDetailsList.add(reportDetails);
        ReportDetails reportDetails1 = createNewReportDetails();
        reportDetails1.setStatus(Status.APPROVED);
        reportDetailsList.add(reportDetails1);
        ReportDetails reportDetails2 = createNewReportDetails();
        reportDetails2.setStatus(Status.PRIVATE);
        reportDetailsList.add(reportDetails2);
        Mockito.doReturn(reportDetailsList)
                .when(reportDetailsDAO)
                .findAllReportsByFilter(1);

        reportDetailsList=reportDetailsService.getReportsByFilter(
                1,
                 Status.PRIVATE,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);

        Mockito.verify(reportDetailsDAO, Mockito.times(1)).findAllReportsByFilter(1);
        Assert.assertEquals(1,reportDetailsList.size());
        Assert.assertEquals(reportDetails2.getReportDetailsId(),reportDetailsList.get(0).getReportDetailsId());
    }

    @Test
    public void getReportsByFilterWithoutPrivateTest() throws ParseException {
        ReportDetails reportDetails = createNewReportDetails();
        List<ReportDetails> reportDetailsList = new ArrayList();
        reportDetailsList.add(reportDetails);
        ReportDetails reportDetails1 = createNewReportDetails();
        reportDetails1.setStatus(Status.APPROVED);
        reportDetailsList.add(reportDetails1);
        ReportDetails reportDetails2 = createNewReportDetails();
        reportDetails2.setStatus(Status.PRIVATE);
        reportDetailsList.add(reportDetails2);
        Mockito.doReturn(reportDetailsList)
                .when(reportDetailsDAO)
                .findAllReportsByFilter(1);

        reportDetailsList=reportDetailsService.getReportsByFilterWithoutPrivate(
                1,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);

        Mockito.verify(reportDetailsDAO, Mockito.times(1)).findAllReportsByFilter(1);
        Assert.assertEquals(2,reportDetailsList.size());
        Assert.assertEquals(reportDetails2.getReportDetailsId(),reportDetailsList.get(0).getReportDetailsId());
    }


}

