package com.epolsoft.wtr.service.ServiceImpl;

import com.epolsoft.wtr.dao.TitleDAO;
import com.epolsoft.wtr.dao.UserDAO;
import com.epolsoft.wtr.model.Title;
import com.epolsoft.wtr.model.User;
import com.epolsoft.wtr.service.TitleService;
import com.epolsoft.wtr.util.ResourceNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TitleServiceImpl implements TitleService {

    private static final Logger LOGGER = LogManager.getLogger(TitleServiceImpl.class);

    @Autowired
    private TitleDAO titleDAO;

    @Autowired
    private UserDAO userDAO;

    @Override
    public Title createTitle(Title title) {
        LOGGER.info("Service method called to create Title: "+title.toString());
        return titleDAO.save(title);
    }

    @Override
    public Title updateTitle(Title title) {
        LOGGER.info("Service method called to update Title;" +
                " title="+title.toString());
        Optional<Title> titleDb = titleDAO.findById(title.getTitleId());

        if (titleDb.isPresent()) {
            LOGGER.info("Title found");
            Title titleUpdate = titleDb.get();
            titleUpdate.setTitleId(title.getTitleId());
            titleUpdate.setTitleName(title.getTitleName());
            LOGGER.info("Title save;" +
                    " title="+titleUpdate.toString());
            titleDAO.save(titleUpdate);
            return titleUpdate;
        } else {
            LOGGER.info("Title not found");
            LOGGER.error("Title not found");
            throw new ResourceNotFoundException("Record not found with id : " + title.getTitleId());
        }
    }

    @Override
    public List<Title> getAllTitles() {
        LOGGER.info("Service method called to view all list of Titles");
        Iterable <Title> allTitles = titleDAO.findAll();
        List <Title> titleList = new ArrayList();
        allTitles.forEach(titleList::add);
        LOGGER.info("Records found: "+titleList.size());
        return titleList;
    }

    @Override
    public Title getTitleById(Integer titleId) {
        LOGGER.info("Service method called to view Title by id="+titleId);
        Optional <Title> titleDb = titleDAO.findById(titleId);

        if (titleDb.isPresent()) {
            LOGGER.info("Title found: " +titleDb.get().toString());
            return titleDb.get();
        } else {
            LOGGER.info("Title not found");
            LOGGER.error("Title not found");
            throw new ResourceNotFoundException("Record not found with id : " + titleId);
        }
    }

    @Override
    public void deleteTitle(Integer titleId) {
        LOGGER.info("Service method called to delete Title by id="+titleId);

        Optional <Title> titleDb = titleDAO.findById(titleId);

        if (titleDb.isPresent()) {
            LOGGER.info("Title found: " +titleDb.get().toString());
            List<User> users=titleDb.get().getUser().stream().collect(Collectors.toList());

            users.forEach(user -> {
                user.setTitle(null);
                userDAO.save(user);
            });

            this.titleDAO.delete(titleDb.get());
        } else {
            LOGGER.info("Title not found");
            LOGGER.error("Title not found");
            throw new ResourceNotFoundException("Record not found with id : " + titleId);
        }
    }
}
