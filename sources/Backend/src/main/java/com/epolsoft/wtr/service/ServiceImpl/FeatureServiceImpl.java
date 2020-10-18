package com.epolsoft.wtr.service.ServiceImpl;

import com.epolsoft.wtr.dao.FeatureDAO;
import com.epolsoft.wtr.dao.ProjectDAO;
import com.epolsoft.wtr.dao.ReportDetailsDAO;
import com.epolsoft.wtr.dao.TaskDAO;
import com.epolsoft.wtr.model.Feature;
import com.epolsoft.wtr.model.ReportDetails;
import com.epolsoft.wtr.model.Tasks;
import com.epolsoft.wtr.model.dto.FeatureDTO;
import com.epolsoft.wtr.service.FeatureService;
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
public class FeatureServiceImpl implements FeatureService{

    private static final Logger LOGGER = LogManager.getLogger(FactorServiceImpl.class);

    @Autowired
    private FeatureDAO featureDao;

    @Autowired
    private ProjectDAO projectDao;

    @Autowired
    private TaskDAO taskDAO;

    @Autowired
    private ReportDetailsDAO reportDetailsDAO;

    @Override
    public Feature createFeature(FeatureDTO featureDTO) {

        LOGGER.info("Service method called to create Feature: "+featureDTO.toString());
        Feature feature = new Feature(
                null,
                featureDTO.getFeatureName(),
                projectDao.findById(featureDTO.getProjectId()).get(),
                null,
                null);

        return featureDao.save(feature);
    }

    @Override
    public Feature updateFeature(FeatureDTO featureDTO) {
        LOGGER.info("Service method called to update Feature;" +
                " feature="+featureDTO.toString());
        Optional<Feature> featureDb = featureDao.findById(featureDTO.getFeatureId());

        if(featureDb.isPresent()){
            LOGGER.info("Feature found");
            Feature featureUpdate = featureDb.get();
            featureUpdate.setFeatureId(featureDTO.getFeatureId());
            featureUpdate.setFeatureName(featureDTO.getFeatureName());
            featureUpdate.setProject(projectDao.findById(featureDTO.getProjectId()).get());
            LOGGER.info("Feature save;" +
                    " feature="+featureUpdate.toString());
            featureDao.save(featureUpdate);
            return featureUpdate;
        }
        else{
            LOGGER.info("Feature not found");
            LOGGER.error("Feature not found");
            throw new ResourceNotFoundException("Record not found with id : " + featureDTO.getFeatureId());
        }
    }

    @Override
    public List<FeatureDTO> getAllFeatures() {
        LOGGER.info("Service method called to view all list of Features");
        Iterable<Feature> allFeatures = featureDao.findAll();
        List<FeatureDTO> featuresList = new ArrayList<>();
        allFeatures.forEach(feature -> {
            featuresList.add(new FeatureDTO(
                    feature.getFeatureId(),
                    feature.getFeatureName(),
                    feature.getProject().getProjectId()));
        });
        LOGGER.info("Records found: "+featuresList.size());
        return featuresList;
    }

    @Override
    public FeatureDTO getFeatureById(Integer featureId) {
        LOGGER.info("Service method called to view Feature by id="+featureId);
        Optional<Feature> featureDb = featureDao.findById(featureId);

        if (featureDb.isPresent()){
            LOGGER.info("Factor found: " +featureDb.get().toString());
            return new FeatureDTO(
                    featureDb.get().getFeatureId(),
                    featureDb.get().getFeatureName(),
                    featureDb.get().getProject().getProjectId());
        }else{
            LOGGER.info("Feature not found");
            LOGGER.error("Feature not found");
            throw new ResourceNotFoundException("Record not found with id : " + featureId);
        }
    }

    @Override
    public void deleteFeature(Integer featureId) {
        LOGGER.info("Service method called to delete Feature by id="+featureId);
        Optional<Feature> featureDb = featureDao.findById(featureId);

        if(featureDb.isPresent()) {
            LOGGER.info("Feature found: " +featureDb.get().toString());
            List<ReportDetails> reportDetails=featureDb.get().getReportDetails().stream().collect(Collectors.toList());

            reportDetails.forEach(report ->
            {
                report.setFeature(null);
                reportDetailsDAO.save(report);
            });

            List<Tasks> tasks=featureDb.get().getTask().stream().collect(Collectors.toList());

            tasks.forEach(task ->
            {
                task.setFeature(null);
                taskDAO.save(task);
            });
            featureDao.delete(featureDb.get());
        }
        else{
            LOGGER.info("Feature not found");
            LOGGER.error("Feature not found");
            throw new ResourceNotFoundException("Record not found with id : " + featureId);
        }
    }
}
