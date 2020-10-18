package com.epolsoft.wtr.service.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.epolsoft.wtr.controller.DepartmentController;
import com.epolsoft.wtr.dao.UserDAO;
import com.epolsoft.wtr.model.User;
import com.epolsoft.wtr.service.DepartmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epolsoft.wtr.util.ResourceNotFoundException;
import com.epolsoft.wtr.model.Department;
import com.epolsoft.wtr.dao.DepartmentDAO;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private static final Logger LOGGER = LogManager.getLogger(DepartmentServiceImpl.class);

    @Autowired
    private DepartmentDAO departmentDao;

    @Autowired
    private UserDAO userDAO;

    @Override
    public Department createDepartment(Department department)
    {
        LOGGER.info("Service method called to create Department;" +
                " department="+department.toString());
        return departmentDao.save(department);
    }

    @Override
    public Department updateDepartment(Department department)
    {
        LOGGER.info("Service method called to update Department;" +
                " department="+department.toString());
        Optional <Department> departmentDb = this.departmentDao.findById(department.getDepartmentId());

        if (departmentDb.isPresent()) {
            LOGGER.info("Department found");
            Department departmentUpdate = departmentDb.get();
            departmentUpdate.setDepartmentId(department.getDepartmentId());
            departmentUpdate.setDepartmentName(department.getDepartmentName());
            LOGGER.info("Department save;" +
                    " department="+departmentUpdate.toString());
            departmentDao.save(departmentUpdate);

            return departmentUpdate;
        } else {
            LOGGER.info("Department not found");
            LOGGER.error("Department not found");
            throw new ResourceNotFoundException("Record not found with id : " + department.getDepartmentId());
        }
    }

    @Override
    public List <Department> getAllDepartment()
    {
        LOGGER.info("Service method called to view all list of Departments");
        Iterable <Department> alldepartments =departmentDao.findAll();
        List <Department> departmentList=new ArrayList();
        alldepartments.forEach(departmentList::add);

        LOGGER.info("Records found: "+departmentList.size());

        return departmentList;
    }

    @Override
    public Department getDepartmentById(Integer departmentId)
    {
        LOGGER.info("Service method called to view Department by id="+departmentId);
        Optional <Department> departmentDb = this.departmentDao.findById(departmentId);

        if (departmentDb.isPresent()) {
            LOGGER.info("Department found: " +departmentDb.get().toString());
            return departmentDb.get();
        } else {
            LOGGER.info("Department not found");
            LOGGER.error("Department not found");
            throw new ResourceNotFoundException("Record not found with id : " + departmentId);
        }
    }

    @Override
    public void deleteDepartment(Integer departmentId)
    {
        LOGGER.info("Service method called to delete Department by id="+departmentId);
        Optional <Department> departmentDb = this.departmentDao.findById(departmentId);

        if (departmentDb.isPresent()) {
            LOGGER.info("Department found: " +departmentDb.get().toString());
            List<User> users=departmentDb.get().getUser().stream().collect(Collectors.toList());

            users.forEach(user ->
            {
                user.setDepartment(null);
                userDAO.save(user);
            });
            this.departmentDao.delete(departmentDb.get());
        } else {
            LOGGER.info("Department not found");
            LOGGER.error("Department not found");
            throw new ResourceNotFoundException("Record not found with id : " + departmentId);
        }
    }
}
