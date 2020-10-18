
package com.epolsoft.wtr.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Test
    public void User() {
        User user = new User(1,"user228","Ivan",  "Ivanov", "ivan228@gmail.com","", null, null, null,null);
        Assert.assertEquals(Optional.of(1).get(),user.getUserId());
        Assert.assertEquals("user228",user.getUserName());
        Assert.assertEquals("Ivan",user.getFirstName());
        Assert.assertEquals("Ivanov",user.getLastName());
        Assert.assertEquals("ivan228@gmail.com",user.getEmail());
        Assert.assertEquals("",user.getPassword());
        Assert.assertEquals(null,user.getTitle());
        Assert.assertEquals(null,user.getDepartment());
        Assert.assertEquals(null,user.getRoles());
        Assert.assertEquals(null,user.getReportDetails());
    }

    @Test
    public void ToString() {
        User user = new User(2,"user228","Ivan",  "Ivanov", "ivan228@gmail.com", null, null);
        Assert.assertEquals(
                "User{" +
                        "userId=2, " +
                        "userName=user228, " +
                        "firstName=Ivan, " +
                        "lastName=Ivanov, " +
                        "email=ivan228@gmail.com, " +
                        "title=null, " +
                        "department=null" +
                        "}",
                user.toString()
        );
    }

}

