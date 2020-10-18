package com.epolsoft.wtr.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartmentTest {

    @Test
    public void Department() {
        Department department=new Department(1,"Department3",null);
        Assert.assertTrue(department.equals(new Department(1,"Department3",null)));
    }

    @Test
    public void ToString() {
        Department department=new Department(10,"Department15");
        Assert.assertEquals(
                "Department [departmentId=10, departmentName=Department15]",
                department.toString()
        );
    }
}
