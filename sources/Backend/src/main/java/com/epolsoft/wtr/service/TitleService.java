package com.epolsoft.wtr.service;

import com.epolsoft.wtr.model.Title;

import java.util.List;

public interface TitleService {
    Title createTitle(Title title);

    Title updateTitle(Title title);

    List<Title> getAllTitles();

    Title getTitleById(Integer titleId);

    void deleteTitle(Integer id);
}