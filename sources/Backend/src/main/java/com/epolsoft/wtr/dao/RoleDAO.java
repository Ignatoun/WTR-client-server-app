package com.epolsoft.wtr.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.epolsoft.wtr.model.Role;
import org.springframework.data.repository.query.Param;

public interface RoleDAO extends CrudRepository<Role, Integer> {
    @Query(value="SELECT * FROM Role where roleName= :roleName", nativeQuery = true)
    Role findByRoleName(@Param("roleName") String roleName);
}
