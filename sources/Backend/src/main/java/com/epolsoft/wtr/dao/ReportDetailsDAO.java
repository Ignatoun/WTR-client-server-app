package com.epolsoft.wtr.dao;

import com.epolsoft.wtr.model.ReportDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
   public interface ReportDetailsDAO extends JpaRepository <ReportDetails, Integer> {
      List<ReportDetails> findAllByReportDetailsDate(Date ReportDetailsDate);
      List<ReportDetails> findAllByReportDetailsDateBetween(
           Date reportDetailsDateStart,
           Date reportDetailsDateEnd);

      @Query(value="SELECT a.* FROM ReportDetails a" +
              " LEFT JOIN Report b ON a.reportDetailsId=b.reportDetailsId" +
              " where userId= :userId and month(date)= :month and year(date)= :year",
              nativeQuery = true)
      List<ReportDetails> findAllReportsByUserIdAndMonth(@Param("userId") Integer userId,@Param("month") Integer month,@Param("year") Integer year);

      @Query(value="SELECT a.* FROM ReportDetails a" +
           " LEFT JOIN Report b ON a.reportDetailsId=b.reportDetailsId" +
           " where userId= :userId",
           nativeQuery = true)
      List<ReportDetails> findAllReportsByFilter(@Param("userId") Integer userId );

   @Query(value="SELECT a.* FROM ReportDetails a" +
           " LEFT JOIN Report b ON a.reportDetailsId=b.reportDetailsId" +
           " where userId= :userId and date=DATE_FORMAT(:date, '%Y-%m-%d')",
           nativeQuery = true)
   List<ReportDetails> findAllReportsByUserIdAndDate(@Param("userId") Integer userId,@Param("date") String date);

}
