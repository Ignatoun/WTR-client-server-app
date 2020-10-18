package com.epolsoft.wtr.service;

import com.epolsoft.wtr.dao.DepartmentDAO;
import com.epolsoft.wtr.dao.UserDAO;
import com.epolsoft.wtr.model.Department;
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
public class DepartmentServiceTest {
    @Autowired
    private DepartmentService departmentService;

    @MockBean
    private DepartmentDAO departmentDao;

    @MockBean
    private UserDAO userDAO;

    @Test
    public void createDepartmentTest()
    {
        Department department=new Department(2,"Department2",null);

        departmentService.createDepartment(department);

        Mockito.verify(departmentDao,Mockito.times(1)).save(department);
    }

    @Test
    public void updateDepartmentTest()
    {
     Department department= new Department(1,"Department1",null);

     Mockito.doReturn(Optional.of(department))
             .when(departmentDao)
             .findById(1);

     departmentService.updateDepartment(department);

     Mockito.verify(departmentDao,Mockito.times(1)).findById(department.getDepartmentId());
     Mockito.verify(departmentDao,Mockito.times(1)).save(department);
     }

     @Test
    public void getAllDepartmentTest()
     {
         Department department=new Department(5,"Department5",null);

         List<Department> list=new ArrayList();
         list.add(department);

         Mockito.doReturn(list)
                 .when(departmentDao)
                 .findAll();

         Assert.assertEquals(list,departmentService.getAllDepartment());
         Mockito.verify(departmentDao,Mockito.times(1)).findAll();
     }

     @Test
    public void getDepartmentByIdTest()
     {
         Department department= new Department(1,"Department1",null);

         Mockito.doReturn(Optional.of(department))
                 .when(departmentDao)
                 .findById(1);

         Assert.assertEquals(department,departmentService.getDepartmentById(department.getDepartmentId()));
     }

     @Test
     public void deleteDepartmentTest()
     {
         Department department= new Department(1,"Department1",new HashSet<User>());

         Mockito.doReturn(Optional.of(department))
                 .when(departmentDao)
                 .findById(1);

         Mockito.doReturn(Optional.of(department).get().getUser())
                 .when(userDAO)
                 .save(null);

         departmentService.deleteDepartment(department.getDepartmentId());

         Mockito.verify(departmentDao,Mockito.times(1)).delete(department);
     }
}