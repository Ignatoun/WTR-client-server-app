package com.epolsoft.wtr.service;

import com.epolsoft.wtr.dao.TitleDAO;
import com.epolsoft.wtr.dao.UserDAO;
import com.epolsoft.wtr.model.Title;
import com.epolsoft.wtr.model.User;
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
public class TitleServiceTest {
    @Autowired
    private TitleService titleService;

    @MockBean
    private TitleDAO titleDao;

    @MockBean
    private UserDAO userDAO;

    @Test
    public void createTitle()
    {
        Title title = new Title(1, "Software Developer", null);

        Mockito.doReturn(title)
                .when(titleDao)
                .save(title);

        Title titleCreate = titleService.createTitle(title);

        Assert.assertEquals(title, titleCreate);
        Mockito.verify(titleDao, Mockito.times(1)).save(title);
    }

    @Test
    public void updateTitle()
    {
        Title title = new Title(1, "Software Developer", null);

        Mockito.doReturn(Optional.of(title))
                .when(titleDao)
                .findById(1);

        Title titleUpdate = titleService.updateTitle(title);

        Assert.assertEquals(title,titleUpdate);
        Mockito.verify(titleDao, Mockito.times(1)).save(title);
        Mockito.verify(titleDao,Mockito.times(1)).findById(title.getTitleId());
    }

    @Test
    public void getAllTitle()
    {
        Title title = new Title(1, "Software Developer", null);

        List<Title> titleList = new ArrayList();
        titleList.add(title);
        Mockito.doReturn(titleList)
                .when(titleDao)
                .findAll();

        List <Title> titleAll =  titleService.getAllTitles();

        Assert.assertEquals(titleList,titleAll);
        Mockito.verify(titleDao,Mockito.times(1)).findAll();
    }

    @Test
    public void  getTitleById()
    {
        Title title = new Title(1, "Software Developer", null);

        Mockito.doReturn(Optional.of(title))
                .when(titleDao)
                .findById(1);

        Title titleGet = titleService.getTitleById(title.getTitleId());

        Assert.assertEquals(title, titleGet);
        Mockito.verify(titleDao,Mockito.times(1)).findById(title.getTitleId());
    }

    @Test
    public void deleteTitle()
    {
        Title title = new Title(1, "Software Developer", new HashSet<User>());

        List<Title> titleList = new ArrayList();
        titleList.add(title);

        title = new Title(2, "Software Tester", new HashSet<User>());

        Mockito.doReturn(Optional.of(title))
                .when(titleDao)
                .findById(title.getTitleId());

        Mockito.doReturn(Optional.of(title).get().getUser())
                .when(userDAO)
                .save(null);

        titleService.deleteTitle(title.getTitleId());

        Mockito.doReturn(titleList)
                .when(titleDao)
                .findAll();

        Iterable<Title> list = titleDao.findAll();
        List <Title> titleList1=new ArrayList();
        list.forEach(titleList1::add);
        Assert.assertEquals(titleList, titleList1);

        Mockito.verify(titleDao,Mockito.times(1)).delete(title);
        Mockito.verify(titleDao,Mockito.times(1)).findById(title.getTitleId());
    }
}
