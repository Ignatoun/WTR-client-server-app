package com.epolsoft.wtr.service.ServiceImpl;


import com.epolsoft.wtr.dao.FactorDAO;
import com.epolsoft.wtr.dao.ReportDetailsDAO;
import com.epolsoft.wtr.model.Factor;
import com.epolsoft.wtr.model.ReportDetails;
import com.epolsoft.wtr.service.FactorService;
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
public class FactorServiceImpl implements FactorService {

    private static final Logger LOGGER = LogManager.getLogger(FactorServiceImpl.class);

    @Autowired
    private FactorDAO factorDao;

    @Autowired
    private ReportDetailsDAO reportDetailsDAO;

    @Override
    public Factor createFactor(Factor factor) {

        LOGGER.info("Service method called to create Factor: "+factor.toString());
        return factorDao.save(factor);
    }

    @Override
    public Factor updateFactor(Factor factor) {
        LOGGER.info("Service method called to update Factor;" +
                " factor="+factor.toString());

        Optional<Factor> factorDb = this.factorDao.findById(factor.getFactorId());

        if (factorDb.isPresent()) {
            LOGGER.info("Factor found");
            Factor factorUpdate = factorDb.get();
            factorUpdate.setFactorId(factor.getFactorId());
            factorUpdate.setFactorName(factor.getFactorName());
            LOGGER.info("Factor save;" +
                    " factor="+factorUpdate.toString());
            factorDao.save(factorUpdate);
            return factorUpdate;
        } else {
            LOGGER.info("Factor not found");
            LOGGER.error("Factor not found");
            throw new ResourceNotFoundException("Record not found with id : " + factor.getFactorId());
        }
    }

    @Override
    public List<Factor> getAllFactor() {
        LOGGER.info("Service method called to view all list of Factors");
        Iterable <Factor> allfactors =factorDao.findAll();
        List <Factor> factorList=new ArrayList();
        allfactors.forEach(factorList::add);
        LOGGER.info("Records found: "+factorList.size());
        return factorList;
    }

    @Override
    public Factor getFactorById(Integer factorId) {
        LOGGER.info("Service method called to view Factor by id="+factorId);
        Optional <Factor> factorDb = this.factorDao.findById(factorId);

        if (factorDb.isPresent()) {
            LOGGER.info("Factor found: " +factorDb.get().toString());
            return factorDb.get();
        } else {
            LOGGER.info("Factor not found");
            LOGGER.error("Factor not found");
            throw new ResourceNotFoundException("Record not found with id : " + factorId);
        }
    }

    @Override
    public void deleteFactor(Integer factorId) {
        LOGGER.info("Service method called to delete Factor by id="+factorId);
        Optional <Factor> factorDb = this.factorDao.findById(factorId);

        if (factorDb.isPresent()) {
            LOGGER.info("Factor found: " +factorDb.get().toString());
            List<ReportDetails> reportDetails =factorDb.get().getReportDetails().stream().collect(Collectors.toList());

            reportDetails.forEach(reportDetail ->
            {
                reportDetail.setFactor(null);
                reportDetailsDAO.save(reportDetail);
            });

            this.factorDao.delete(factorDb.get());
        } else {
            LOGGER.info("Factor not found");
            LOGGER.error("Factor not found");
            throw new ResourceNotFoundException("Record not found with id : " + factorId);
        }
    }
}