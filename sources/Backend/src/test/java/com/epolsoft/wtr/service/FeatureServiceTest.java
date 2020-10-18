package com.epolsoft.wtr.service;

import com.epolsoft.wtr.dao.FeatureDAO;
import com.epolsoft.wtr.dao.ProjectDAO;
import com.epolsoft.wtr.model.*;
import com.epolsoft.wtr.model.dto.FeatureDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FeatureServiceTest {

    @Autowired
    private FeatureService featureService;

    @MockBean
    private FeatureDAO featureDao;

    @MockBean
    private ProjectDAO projectDAO;

    @Test
    public void createFeature()
    {
        FeatureDTO featureDTO=new FeatureDTO(1,"project database development",null);

        Feature feature = new Feature(null,
                featureDTO.getFeatureName(),
                new Project(),
                null,
                 null);

        Mockito.doReturn(feature)
                .when(featureDao)
                .save(Mockito.any(Feature.class));

        Mockito.doReturn(Optional.of(new Project()))
                .when(projectDAO)
                .findById(null);

        Feature featureCreate = featureService.createFeature(featureDTO);

        Assert.assertEquals(featureCreate.getFeatureId(),feature.getFeatureId());
        Assert.assertEquals(featureCreate.getFeatureName(),feature.getFeatureName());
        Assert.assertEquals(featureCreate.getProject(),feature.getProject());

        Mockito.verify(featureDao, Mockito.times(1)).save(Mockito.any(Feature.class));
    }

    @Test
    public void updateFeature()
    {
        FeatureDTO featureDTO=new FeatureDTO(1,"project database development",null);

        Feature feature = new Feature(
                1,
                "project database development",
                new Project(),
                null,
                null);

        Mockito.doReturn(Optional.of(feature))
                .when(featureDao)
                .findById(1);

        Mockito.doReturn(feature)
                .when(featureDao)
                .save(Mockito.any(Feature.class));

        Mockito.doReturn(Optional.of(new Project()))
                .when(projectDAO)
                .findById(null);

        Feature featureUpdate = featureService.updateFeature(featureDTO);

        Assert.assertEquals(featureUpdate.getFeatureId(),feature.getFeatureId());
        Assert.assertEquals(featureUpdate.getFeatureName(),feature.getFeatureName());
        Assert.assertEquals(featureUpdate.getProject(),feature.getProject());

        Mockito.verify(featureDao, Mockito.times(1)).save(Mockito.any(Feature.class));
        Mockito.verify(featureDao,Mockito.times(1)).findById(feature.getFeatureId());
    }

    @Test
    public void getAllFeatures()
    {
        Feature feature = new Feature(
                1,
                "project database development",
                new Project(1,null,null,null,null,null),
                null,
                null);

        List<Feature> featureList = new ArrayList();
        featureList.add(feature);
        Mockito.doReturn(featureList)
                .when(featureDao)
                .findAll();

        List <FeatureDTO> featureAll =  featureService.getAllFeatures();
        for(int i=0;i<featureAll.size();i++) {
            Assert.assertEquals(featureList.get(i).getFeatureId(), featureAll.get(i).getFeatureId());
            Assert.assertEquals(featureList.get(i).getFeatureName(), featureAll.get(i).getFeatureName());
            Assert.assertEquals(featureList.get(i).getProject().getProjectId(), featureAll.get(i).getProjectId());
        }
        Mockito.verify(featureDao,Mockito.times(1)).findAll();
    }

    @Test
    public void getFeatureById()
    {
        Feature feature = new Feature(
                1,
                "project database development",
                new Project(1,null,null,null,null,null),
                null,
                null);

        Mockito.doReturn(Optional.of(feature))
                .when(featureDao)
                .findById(1);

        FeatureDTO featureGet = featureService.getFeatureById(feature.getFeatureId());

        Assert.assertEquals(feature.getFeatureId(), featureGet.getFeatureId());
        Assert.assertEquals(feature.getFeatureName(), featureGet.getFeatureName());
        Assert.assertEquals(feature.getProject().getProjectId(), featureGet.getProjectId());
        Mockito.verify(featureDao,Mockito.times(1)).findById(feature.getFeatureId());
    }

    @Test
    public void deleteFeature()
    {
        Feature feature = new Feature(
                1,
                "project database development",
                new Project(),
                new HashSet<ReportDetails>(),
                new HashSet<Tasks>());

        Mockito.doReturn(Optional.of(feature))
                .when(featureDao)
                .findById(feature.getFeatureId());

        featureService.deleteFeature(feature.getFeatureId());

        Mockito.verify(featureDao,Mockito.times(1)).delete(feature);
    }

}
