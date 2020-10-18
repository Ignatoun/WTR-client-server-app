package com.epolsoft.wtr.service.ServiceImpl;

import com.epolsoft.wtr.dao.*;
import com.epolsoft.wtr.model.ReportDetails;
import com.epolsoft.wtr.model.Role;
import com.epolsoft.wtr.model.User;
import com.epolsoft.wtr.model.dto.UserDTO;
import com.epolsoft.wtr.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epolsoft.wtr.util.ResourceNotFoundException;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserDAO userDao;

    @Autowired
    private TitleDAO titleDao;

    @Autowired
    private DepartmentDAO departmentDao;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private ReportDetailsDAO reportDetailsDAO;

    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        LOGGER.info("Service method called to view all list of Users");
        Iterable<User> userList = userDao.findAll();
        List<User> users = new ArrayList<>();
        userList.forEach(users::add);
        LOGGER.info("Records found: "+users.size());
        return users;
    }

    @Override
    public List<User> getUsersByFilter(String firstName, String lastName) {
        if(firstName==null)
            firstName="";
        if (lastName==null)
            lastName="";
        LOGGER.info("Service method called to view all Users by Filter");
        List<User> userList = userDao.filterByFirstNameAndLastName(firstName,lastName);
        LOGGER.info("Records found: "+userList.size());
        return userList;
    }

    @Override
    public User getUserById(Integer userId) {
        LOGGER.info("Service method called to view User by id="+userId);
        Optional<User> userOptional = userDao.findById(userId);
        if(userOptional.isPresent()) {
            LOGGER.info("User found: " +userOptional.get().toString());
            return userOptional.get();
        } else {
            LOGGER.info("User not found");
            LOGGER.error("User not found");
            throw new ResourceNotFoundException("No user record exist for given id");
        }
    }

    @Override
    public User getUserByName(String userName) {
        LOGGER.info("Service method called to view User by Name="+userName);
        User user = userDao.findByUsername(userName);
        if(user!=null) {
            LOGGER.info("User found: " +user.toString());
            return user;
        } else {
            LOGGER.info("User not found");
            LOGGER.error("User not found");
            throw new ResourceNotFoundException("No user record exist for given Name");
        }
    }

    @Override
    public User createUser(UserDTO userDto) {
        LOGGER.info("Service method called to create User: "+userDto.toString());
        Set<Role> roles=new HashSet<>(Arrays.asList(roleDAO.findById(1).get()));
        User user=new User(
                null,
                userDto.getUserName(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail(),
                bCryptPasswordEncoder.encode(userDto.getPassword()),
                titleDao.findById(userDto.getTitleId()).get(),
                departmentDao.findById(userDto.getDepartmentId()).get(),
                roles,
                null);

        User user1=userDao.save(user);

        Role role=roleDAO.findById(1).get();
        role.getUser().add(user);
        roleDAO.save(role);

        return user1;
    }

    @Override
    public User updateUser(UserDTO userDto) {
        LOGGER.info("Service method called to update User;" +
                " user="+userDto.toString());
        Optional <User> userDb = userDao.findById(userDto.getUserId());

        if (userDb.isPresent()) {
            LOGGER.info("User found");
            User userUpdate = userDb.get();

            userUpdate.setUserId(userDto.getUserId());
            userUpdate.setUserName(userDto.getUserName());
            userUpdate.setFirstName(userDto.getFirstName());
            userUpdate.setLastName(userDto.getLastName());
            userUpdate.setEmail(userDto.getEmail());
            userUpdate.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
            userUpdate.setTitle(titleDao.findById(userDto.getTitleId()).get());
            userUpdate.setDepartment(departmentDao.findById(userDto.getDepartmentId()).get());

            Iterable <Role> allroles =roleDAO.findAll();
            List <Role> roleList=new ArrayList();
            allroles.forEach(roleList::add);
            List<Role> roles=new ArrayList<>();
            List<Integer> rolesId = userDto.getRoleId().stream().collect(Collectors.toList());

            Set<Role> roleSet=new HashSet<>();

            roleList.forEach(role->{
                List<User> newUser=role.getUser().stream().collect(Collectors.toList());
                for(int i=0;i<newUser.size();i++)
                    if(newUser.get(i).getUserId()==userDto.getUserId())
                        newUser.remove(i);
                role.setUser(newUser.stream().collect(Collectors.toSet()));
                roleDAO.save(role);
            });

            rolesId.forEach(rId->
            {
                roles.add(roleDAO.findById(rId).get());
            });

            roles.forEach(role ->roleSet.add(role));

            userUpdate.setRoles(roleSet);

            roles.forEach(role ->
            {
                role.getUser().add(userUpdate);
                roleDAO.save(role);
            });

            LOGGER.info("User save;" +
                    " user="+userUpdate.toString());
            return userDao.save(userUpdate);
        } else {
            LOGGER.info("User not found");
            LOGGER.error("User not found");
            throw new ResourceNotFoundException("No user record exist for given id");
        }
    }

    @Override
    public boolean validateUsername(String username) {
        User user= userDao.findByUsername(username);
        if (user!=null)
            throw new ResourceNotFoundException("User with this username exists");
        else
            return true;
    }

    @Override
    public void deleteUser(Integer id) {
        LOGGER.info("Service method called to delete User by id="+id);
        Optional<User> user = userDao.findById(id);

        if(user.isPresent()) {
            LOGGER.info("User found: " +user.get().toString());
            List<ReportDetails> reportDetails=user.get().getReportDetails().stream().collect(Collectors.toList());

            reportDetails.forEach(report ->
            {
                report.getUsers().remove(user.get());
                reportDetailsDAO.save(report);
            });

            List<Role> roles= user.get().getRoles().stream().collect(Collectors.toList());

            roles.forEach(role ->
            {
                role.getUser().remove(user.get());
                roleDAO.save(role);
            });

            userDao.delete(user.get());
        } else {
            LOGGER.info("User not found");
            LOGGER.error("User not found");
            throw new ResourceNotFoundException("No user record exist for given id");
        }
    }
}
