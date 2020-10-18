package com.epolsoft.wtr.model;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
class FeatureTest {

    Feature feature = new Feature();

    @Test
    void getFeatureId() {
        feature.setFeatureId(5);
        Assert.assertTrue(feature.getFeatureId().equals(5));
    }

    @Test
    void getFeatureName() {
        feature.setFeatureName("Feature1");
        Assert.assertTrue(feature.getFeatureName().equals("Feature1"));
    }

}