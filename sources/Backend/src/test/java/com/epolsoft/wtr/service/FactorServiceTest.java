package com.epolsoft.wtr.service;

import com.epolsoft.wtr.dao.FactorDAO;
import com.epolsoft.wtr.dao.ReportDetailsDAO;
import com.epolsoft.wtr.model.Factor;
import com.epolsoft.wtr.model.ReportDetails;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FactorServiceTest {
    @Autowired
    private FactorService factorService;

    @MockBean
    private FactorDAO factorDao;

    @MockBean
    private ReportDetailsDAO reportDetailsDAO;

    @Test
    public void createFactor()
    {
        Factor factor = new Factor(4,"Sick or Care Absence", null);

        Mockito.doReturn(factor)
                .when(factorDao)
                .save(factor);

        Factor factorCreate = factorService.createFactor(factor);

        Assert.assertEquals(factor,factorCreate);
        Mockito.verify(factorDao, Mockito.times(1)).save(factor);
    }

    @Test
    public void updateFactor()
    {
        Factor factor = new Factor(4,"Sick or Care Absence", null);

        Mockito.doReturn(Optional.of(factor))
                .when(factorDao)
                .findById(4);

        Factor factorUpdate = factorService.updateFactor(factor);

        Assert.assertEquals(factor,factorUpdate);
        Mockito.verify(factorDao, Mockito.times(1)).save(factor);
        Mockito.verify(factorDao,Mockito.times(1)).findById(factor.getFactorId());
    }

    @Test
    public void getAllFactor()
    {
        Factor factor = new Factor(4,"Sick or Care Absence", null);

        List<Factor> factorList = new ArrayList();
        factorList.add(factor);
        Mockito.doReturn(factorList)
                .when(factorDao)
                .findAll();

        List <Factor> factorAll =  factorService.getAllFactor();

        Assert.assertEquals(factorList,factorAll);
        Mockito.verify(factorDao,Mockito.times(1)).findAll();
    }

    @Test
    public void getFactorById()
    {
        Factor factor = new Factor(4,"Sick or Care Absence", null);

        Mockito.doReturn(Optional.of(factor))
                .when(factorDao)
                .findById(4);

        Factor factorGet = factorService.getFactorById(factor.getFactorId());

        Assert.assertEquals(factor, factorGet);
        Mockito.verify(factorDao,Mockito.times(1)).findById(factor.getFactorId());
    }

    @Test
    public void deleteFactor()
    {
        Factor factor = new Factor(4,"Sick or Care Absence", new HashSet<ReportDetails>());

        List<Factor> factorList = new ArrayList();
        factorList.add(factor);

        factor = new Factor(1,"Standard", new HashSet<ReportDetails>());

        Mockito.doReturn(Optional.of(factor))
                .when(factorDao)
                .findById(factor.getFactorId());

        Mockito.doReturn(Optional.of(factor).get().getReportDetails())
                .when(reportDetailsDAO)
                .save(null);

        factorService.deleteFactor(factor.getFactorId());

        Mockito.doReturn(factorList)
                .when(factorDao)
                .findAll();

        Iterable<Factor> list = factorDao.findAll();
        List <Factor> factorList1=new ArrayList();
        list.forEach(factorList1::add);
        Assert.assertEquals(factorList,factorList1);

        Mockito.verify(factorDao,Mockito.times(1)).delete(factor);
        Mockito.verify(factorDao,Mockito.times(1)).findById(factor.getFactorId());
    }
}
