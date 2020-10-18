package com.epolsoft.wtr.dao;

import com.epolsoft.wtr.model.Feature;
import com.epolsoft.wtr.model.dto.FeatureDTO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value={"/create-feature-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value={"/create-feature-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class FeatureDAOTest {

    @Autowired
    private FeatureDAO featureDAO;

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void createFeatureTest(){
        Feature feature = new Feature(6, "Feature86",
                projectDAO.findById(1).get(),null, null);
        Feature feature1;
        FeatureDTO feature2;

        feature1 = featureDAO.save(feature);
        Assert.assertEquals(feature.getFeatureId(), feature1.getFeatureId());

        feature2 = jdbcTemplate.queryForObject(
                "SELECT * FROM wtrtest.Feature where featureId = ? ",
                new Object[] { feature.getFeatureId() },
                new BeanPropertyRowMapper<FeatureDTO>(FeatureDTO.class)
        );

        assert feature2 != null;
        Assert.assertEquals(feature.getFeatureId(), feature2.getFeatureId());
        Assert.assertEquals(feature.getFeatureName(), feature2.getFeatureName());
        Assert.assertEquals(feature.getProject().getProjectId(), feature2.getProjectId());
    }

    @Test
    void findAllFeaturesTest(){
        Iterable <Feature> allFeatures = featureDAO.findAll();
        List<Feature> featureList = new ArrayList();
        allFeatures.forEach(featureList::add);

        Assert.assertEquals(5, featureList.size());

        List<FeatureDTO> featureList1;
        featureList1= jdbcTemplate.query(
                "SELECT * FROM wtrtest.Feature",
                new BeanPropertyRowMapper<FeatureDTO>(FeatureDTO.class)
        );

        for (int i = 0; i < featureList.size(); i++)
        {
            Assert.assertEquals(featureList.get(i).getFeatureId(), featureList1.get(i).getFeatureId());
            Assert.assertEquals(featureList.get(i).getFeatureName(), featureList1.get(i).getFeatureName());
            Assert.assertEquals(featureList.get(i).getProject().getProjectId(), featureList1.get(i).getProjectId());
        }
    }

    @Test
    void findByIdFeaturesTest(){
        Feature feature = new Feature(8, "Feature8",
                projectDAO.findById(1).get(), null, null);
        Feature feature1 = featureDAO.save(feature);

        FeatureDTO feature2 = jdbcTemplate.queryForObject(
                "SELECT * FROM wtrtest.Feature where featureId = ? ",
                new Object[] { feature1.getFeatureId() },
                new BeanPropertyRowMapper<FeatureDTO>(FeatureDTO.class)
        );

        assert feature2 != null;
        Optional<Feature> feature3 = featureDAO.findById(feature1.getFeatureId());
        Assert.assertTrue(feature3.isPresent());
        Assert.assertEquals(feature2.getFeatureId(), feature3.get().getFeatureId());
        Assert.assertEquals(feature2.getFeatureName(), feature3.get().getFeatureName());
        Assert.assertEquals(feature2.getProjectId(), feature3.get().getProject().getProjectId());
    }

    @Test
    void deleteByIdFeatureTest(){
        Feature feature = new Feature(9, "Feature9",
                projectDAO.findById(1).get(), null, null);
        Feature feature1 = featureDAO.save(feature);
        Assert.assertTrue(featureDAO.findById(feature1.getFeatureId()).isPresent());
        featureDAO.deleteById(feature1.getFeatureId());
        Assert.assertFalse(featureDAO.findById(feature1.getFeatureId()).isPresent());
    }

    @Test
    void deleteAllFeaturesTest() {
        featureDAO.deleteAll();
        Assert.assertEquals(0, featureDAO.count());
    }
}
