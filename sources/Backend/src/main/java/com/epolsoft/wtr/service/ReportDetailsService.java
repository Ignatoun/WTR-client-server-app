package com.epolsoft.wtr.service;

import com.epolsoft.wtr.model.Enums.Status;
import com.epolsoft.wtr.model.ReportDetails;
import com.epolsoft.wtr.model.dto.ReportDetailsDTO;

import java.util.Date;
import java.util.List;

public interface ReportDetailsService {
        List<ReportDetails> getAllReportDetails();
        ReportDetails getReportDetailsById(Integer reportDetailsId);
        ReportDetails getReportDetailsByIdToUser(Integer reportDetailsId,Integer userId);
        ReportDetails createReportDetails(ReportDetailsDTO reportDetailsDto);
        ReportDetails updateReportDetails(ReportDetailsDTO reportDetailsDto);
        void deleteReportDetails(Integer reportDetailsId);
        List<ReportDetails> createListOfReportDetails(List<ReportDetailsDTO> reportDetailsDtoList);
        List<ReportDetails> updateListOfReportDetails(List<ReportDetailsDTO> reportDetailsDtoList);
        List<ReportDetails> getAllReportDetailsByDate(Date date);
        List<ReportDetails> getAllReportDetailsByDateAndUser(String date, Integer userId);
        List<ReportDetails> getAllReportDetailsByBetweenOfDates(Date dateStart, Date dateEnd);
        void deleteAllReportDetails();
        List<Integer> getAllDatesWithoutReports(Integer userId, Integer month, Integer year);
        List<ReportDetails> getReportsByFilter(Integer userId, Status status, Integer projectId, Integer taskId, Integer detailedTaskId, Integer featureId, Integer factorId, Integer locationId, Date dateStart , Date dateEnd);
        List<ReportDetails> getReportsByFilterWithoutPrivate(Integer userId, Status status, Integer projectId, Integer taskId, Integer detailedTaskId, Integer featureId, Integer factorId, Integer locationId, Date dateStart , Date dateEnd);
        List<ReportDetails> getAllReportsByFilter( Status status, Integer projectId, Integer taskId, Integer detailedTaskId, Integer featureId, Integer factorId, Integer locationId, Date dateStart , Date dateEnd);

}
