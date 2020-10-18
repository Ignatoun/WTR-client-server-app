package com.epolsoft.wtr.service;

import java.util.List;

import com.epolsoft.wtr.model.Department;

public interface DepartmentService {
    Department createDepartment(Department department);

    Department updateDepartment(Department department);

    List < Department > getAllDepartment();

    Department getDepartmentById(Integer departmentId);

    void deleteDepartment(Integer id);
}
