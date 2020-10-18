package com.epolsoft.wtr.service;

import com.epolsoft.wtr.model.Factor;

import java.util.List;

public interface FactorService {
    Factor createFactor(Factor factor);

    Factor updateFactor(Factor factor);

    List< Factor > getAllFactor();

    Factor getFactorById(Integer FactorId);

    void deleteFactor(Integer id);
}
