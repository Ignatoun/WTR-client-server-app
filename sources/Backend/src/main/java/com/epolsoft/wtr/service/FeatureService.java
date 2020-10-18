package com.epolsoft.wtr.service;

import com.epolsoft.wtr.model.Feature;
import com.epolsoft.wtr.model.dto.FeatureDTO;

import java.util.List;

public interface FeatureService {
    Feature createFeature(FeatureDTO featureDTO);

    Feature updateFeature(FeatureDTO featureDTO);

    List<FeatureDTO> getAllFeatures();

    FeatureDTO getFeatureById(Integer featureId);

    void deleteFeature(Integer featureId);
}
