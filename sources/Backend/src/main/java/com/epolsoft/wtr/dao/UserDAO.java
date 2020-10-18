package com.epolsoft.wtr.dao;

import com.epolsoft.wtr.model.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDAO extends CrudRepository<User, Integer> {

    @Query(value="SELECT * FROM User WHERE userName= :userName", nativeQuery = true)
    User findByUsername(@Param("userName") String userName);

    @Query(value="select * FROM User " +
            "WHERE firstName LIKE CONCAT('%',:firstName,'%') AND lastName LIKE CONCAT('%',:lastName,'%') " +
            "OR firstName LIKE CONCAT('%',:lastName,'%') AND lastName LIKE CONCAT('%',:firstName,'%')",
            nativeQuery = true)
    List<User> filterByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
