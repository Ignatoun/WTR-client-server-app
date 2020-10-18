package com.epolsoft.wtr.controller;

import com.epolsoft.wtr.model.Enums.Status;
import com.epolsoft.wtr.model.ReportDetails;
import com.epolsoft.wtr.model.dto.ReportDetailsDTO;
import com.epolsoft.wtr.security.jwt.JwtTokenProvider;
import com.epolsoft.wtr.service.ReportDetailsService;
import com.epolsoft.wtr.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.HashSet;
import java.util.Arrays;

@RestController
@Api(value = "ReportDetails", description = "Everything about ReportDetails")
public class ReportDetailsController {

    private static final Logger LOGGER = LogManager.getLogger(ReportDetailsController.class);

    private final ReportDetailsService reportDetailsService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    public ReportDetailsController(ReportDetailsService reportDetailsService) {
        this.reportDetailsService = reportDetailsService;
    }

    public static Date setDateOneDay(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("yyyy-MM-dd");
        Date date1 = dateFormat.parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        cal.add(Calendar.DAY_OF_YEAR, 1);
        return cal.getTime();
    }

    @ApiOperation(value="View all list of ReportsDetails")
    @GetMapping("/manager/reportDetails")
    public ResponseEntity<List<ReportDetails>> getAllReportDetails(){
        LOGGER.info("Controller method called to view all list of ReportDetails");
        List<ReportDetails> list = reportDetailsService.getAllReportDetails();
        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @ApiOperation(value="View ReportsDetails by Date")
    @GetMapping("/admin/reportDetails/date/{date}")
    public  List<ReportDetails> getDate(@PathVariable("date")String date) throws ParseException {
        LOGGER.info("Controller method called to view Report by Date="+date.toString());
        return reportDetailsService.getAllReportDetailsByDate(setDateOneDay(date));
    }

    @ApiOperation(value="View ReportsDetails by userId and Date")
    @GetMapping("/user/reportDetails/{date}")
    public  List<ReportDetails> getReportByDateAndUserId(@RequestHeader("Authorization") String token,
                                                         @PathVariable("date")String date) throws ParseException {
        Integer userId=userService
                .getUserByName(jwtTokenProvider.getUsername(token.substring(7,token.length())))
                .getUserId();

        LOGGER.info("Controller method called to view Report:" +
                " Date="+date+", userId="+userId);
        return reportDetailsService.getAllReportDetailsByDateAndUser(date,userId);
    }

    @ApiOperation(value="View days without reports")
    @GetMapping("/user/reportDetails/date/{month}/{year}")
    public  List<Integer> getDates(@RequestHeader("Authorization") String token,
                                   @PathVariable("month")Integer month,
                                   @PathVariable("year")Integer year) throws ParseException {
        Integer userId=userService
                .getUserByName(jwtTokenProvider.getUsername(token.substring(7,token.length())))
                .getUserId();

        LOGGER.info("Controller method called to view all days without reports by params:" +
                " userId=" + userId + ", month=" + month + ", year=" + year);
        return reportDetailsService.getAllDatesWithoutReports(userId,month,year);
    }

    @ApiOperation(value="View ReportDetails by filter")
    @RequestMapping(value="/user/reportDetails/filter", method = RequestMethod.GET)
    public List<ReportDetails> getReports(@RequestHeader("Authorization") String token,
                                          @RequestParam(value="status", required = false) Status status,
                                          @RequestParam(value = "projectId", required=false) Integer projectId,
                                          @RequestParam(value="taskId", required=false) Integer taskId ,
                                          @RequestParam(value="detailedTaskId", required=false) Integer detailedTaskId,
                                          @RequestParam(value="featureId", required = false) Integer featureId,
                                          @RequestParam(value="factorId", required = false) Integer factorId,
                                          @RequestParam(value="locationId", required = false) Integer locationId,
                                          @RequestParam(value="dateStart", required = false) String startDate,
                                          @RequestParam(value="dateEnd", required = false) String endDate) throws ParseException {
        LOGGER.info("Controller method called to view all reports by filter");
        Date dateStart = new Date();
        Date dateEnd = new Date();
        if(startDate!=null || endDate!=null) {
            if (startDate!=null)
                dateStart = setDateOneDay(startDate);
            else
                dateStart = null;
            if (endDate!=null)
                dateEnd = setDateOneDay(endDate);
            else
                dateEnd = null;
        }
        else{
            dateStart=null;
            dateEnd=null;
        }

        Integer userId=userService
                .getUserByName(jwtTokenProvider.getUsername(token.substring(7,token.length())))
                .getUserId();

        return reportDetailsService.getReportsByFilter(userId, status, projectId, taskId, detailedTaskId, featureId, factorId, locationId, dateStart ,  dateEnd);
    }

    @ApiOperation(value="View ReportDetails by filter to Admin and Manager")
    @RequestMapping(value="/manager/reportDetails/filter/{userId}", method = RequestMethod.GET)
    public List<ReportDetails> getReportsToManagerByUserId(@PathVariable ("userId") Integer userId,
                                                           @RequestParam(value="status", required = false) Status status,
                                                           @RequestParam(value = "projectId", required=false) Integer projectId,
                                                           @RequestParam(value="taskId", required=false) Integer taskId ,
                                                           @RequestParam(value="detailedTaskId", required=false) Integer detailedTaskId,
                                                           @RequestParam(value="featureId", required = false) Integer featureId,
                                                           @RequestParam(value="factorId", required = false) Integer factorId,
                                                           @RequestParam(value="locationId", required = false) Integer locationId,
                                                           @RequestParam(value="dateStart", required = false) String startDate,
                                                           @RequestParam(value="dateEnd", required = false) String endDate) throws ParseException {
        LOGGER.info("Controller method called to view all reports by filter to Admin and Manager");
        Date dateStart = new Date();
        Date dateEnd = new Date();
        if(startDate!=null || endDate!=null) {
            if (startDate!=null)
                dateStart = setDateOneDay(startDate);
            else
                dateStart = null;
            if (endDate!=null)
                dateEnd = setDateOneDay(endDate);
            else
                dateEnd = null;
        }
        else{
            dateStart=null;
            dateEnd=null;
        }

        return reportDetailsService.getReportsByFilterWithoutPrivate(userId, status, projectId, taskId, detailedTaskId, featureId, factorId, locationId, dateStart ,  dateEnd);
    }

    @ApiOperation(value="View ReportDetails by filter to Manager")
    @RequestMapping(value="/manager/reportDetails/filter", method = RequestMethod.GET)
    public List<ReportDetails> getReportsToManager(@RequestParam(value="status", required = false) Status status,
                                                   @RequestParam(value = "projectId", required=false) Integer projectId,
                                                   @RequestParam(value="taskId", required=false) Integer taskId ,
                                                   @RequestParam(value="detailedTaskId", required=false) Integer detailedTaskId,
                                                   @RequestParam(value="featureId", required = false) Integer featureId,
                                                   @RequestParam(value="factorId", required = false) Integer factorId,
                                                   @RequestParam(value="locationId", required = false) Integer locationId,
                                                   @RequestParam(value="dateStart", required = false) String startDate,
                                                   @RequestParam(value="dateEnd", required = false) String endDate) throws ParseException {
        LOGGER.info("Controller method called to view all reports by filter to Manager");
        Date dateStart = new Date();
        Date dateEnd = new Date();
        if(startDate!=null || endDate!=null) {
            if (startDate!=null)
                dateStart = setDateOneDay(startDate);
            else
                dateStart = null;
            if (endDate!=null)
                dateEnd = setDateOneDay(endDate);
            else
                dateEnd = null;
        }
        else{
            dateStart=null;
            dateEnd=null;
        }

        return reportDetailsService.getAllReportsByFilter(status,projectId,taskId,detailedTaskId,featureId,factorId,locationId,dateStart,dateEnd);
    }


    @ApiOperation(value="View ReportsDetails by start day and end day")
    @GetMapping("/admin/reportDetails/date/{dateStart}/{dateEnd}")
    public  List<ReportDetails> getDateBetween(@PathVariable("dateStart")String dateS,
                                               @PathVariable("dateEnd")String dateE) throws ParseException {
        Date dateStart = setDateOneDay(dateS);
        Date dateEnd = setDateOneDay(dateE);
        LOGGER.info("Controller method called to view list of ReportDetails by start day and end day;" +
                " start day=" + dateStart.toString() + ", end day=" + dateEnd.toString());
        return reportDetailsService.getAllReportDetailsByBetweenOfDates(dateStart,dateEnd);
    }

    @ApiOperation(value="View ReportDetails by Id")
    @GetMapping("/admin/reportDetails/{id}")
    public ResponseEntity<ReportDetails> getReportDetailsById(@PathVariable("id") Integer id) {
        LOGGER.info("Controller method called to view report by Id=" + id);
        ReportDetails reportDetails = reportDetailsService.getReportDetailsById(id);
        return new ResponseEntity<>(reportDetails, new HttpHeaders(), HttpStatus.OK);
    }

    @ApiOperation(value="View ReportDetails by Id to User")
    @GetMapping("/user/reportDetails/get/{id}")
    public ResponseEntity<ReportDetails> getReportDetailsByIdToUser(@PathVariable("id") Integer id,
                                                                    @RequestHeader("Authorization") String token) {
        Integer userId=userService
                .getUserByName(jwtTokenProvider.getUsername(token.substring(7,token.length())))
                .getUserId();

        LOGGER.info("Controller method called to view report" +
                " by Id=" + id+
                ", to userId="+userId);

        ReportDetails reportDetails = reportDetailsService.getReportDetailsByIdToUser(id,userId);
        return new ResponseEntity<>(reportDetails, new HttpHeaders(), HttpStatus.OK);
    }

    @ApiOperation(value = "Update ReportDetails by Id")
    @PutMapping("/manager/reportDetails/{id}")
    public ResponseEntity<ReportDetails> updateReportDetails(@PathVariable("id") Integer id,
                                                             @RequestBody ReportDetailsDTO reportDetailsDto){
        reportDetailsDto.setReportDetailsId(id);
        LOGGER.info("Controller method called to update Report by" +
                " id="+id+", newReport="+reportDetailsDto.toString());
        ReportDetails updated = reportDetailsService.updateReportDetails(reportDetailsDto);

        return new ResponseEntity<>(updated, new HttpHeaders(), HttpStatus.OK);
    }

    @ApiOperation(value = "Update List of ReportDetails by Id")
    @PutMapping("/user/reportDetails/list")
    public ResponseEntity<List<ReportDetails>> updateListOfReportDetails(@RequestHeader("Authorization") String token,
                                                                         @RequestBody List<ReportDetailsDTO> reportDetailsDtoList){
        Integer userId=userService
                .getUserByName(jwtTokenProvider.getUsername(token.substring(7,token.length())))
                .getUserId();

        for(int i = 0; i < reportDetailsDtoList.size(); i++) {
            reportDetailsDtoList.get(i).setUsersId(new HashSet<>(Arrays.asList(userId)));
            LOGGER.info("Controller method called to update Report by" +
                    " id=" + userId + ", newReport=" + reportDetailsDtoList.get(i).toString());
        }
        List<ReportDetails> updated = reportDetailsService.updateListOfReportDetails(reportDetailsDtoList);

        return new ResponseEntity<>(updated, new HttpHeaders(), HttpStatus.OK);
    }

    @ApiOperation(value="Add new ReportDetails")
    @PostMapping("/user/reportDetails")
    public ResponseEntity<ReportDetails> createReportDetails(@RequestHeader("Authorization") String token,
                                                             @RequestBody ReportDetailsDTO reportDetailsDto){
        Integer userId=userService
                .getUserByName(jwtTokenProvider.getUsername(token.substring(7,token.length())))
                .getUserId();

        reportDetailsDto.setUsersId(new HashSet<>(Arrays.asList(userId)));

        LOGGER.info("Controller method called to create report;" +
                "userId=" + userId + "report=" + reportDetailsDto);

        return ResponseEntity.ok().body(this.reportDetailsService.createReportDetails(reportDetailsDto));
    }

    @ApiOperation(value="Add List of ReportDetails")
    @PostMapping("/user/reportDetails/list")
    public ResponseEntity<List<ReportDetails>> createListOfReportDetails(@RequestHeader("Authorization") String token,
                                                                         @RequestBody List<ReportDetailsDTO> reportDetailsDtoList){

        Integer userId=userService
                .getUserByName(jwtTokenProvider.getUsername(token.substring(7,token.length())))
                .getUserId();

        for (ReportDetailsDTO reportDetailsDto : reportDetailsDtoList) {
            reportDetailsDto.setUsersId(new HashSet<>(Arrays.asList(userId)));
            LOGGER.info("Controller method called to create report;" +
                    "userId=" + userId + "report=" + reportDetailsDto);
        }
        return ResponseEntity.ok().body(this.reportDetailsService.createListOfReportDetails(reportDetailsDtoList));
    }

    @ApiOperation(value="Delete ReportDetails by Id")
    @DeleteMapping("/user/reportDetails/{id}")
    public HttpStatus deleteReportDetailsById(@PathVariable("id") Integer id){
        LOGGER.info("Controller method called to delete Report by id=" + id);
        reportDetailsService.deleteReportDetails(id);
        return HttpStatus.OK;
    }

    @ApiOperation(value="Delete all ReportDetails")
    @DeleteMapping("/admin/reportDetails")
    public HttpStatus deleteAllReportDetails(){
        LOGGER.info("Controller method called to delete all list of ReportDetails");
        reportDetailsService.deleteAllReportDetails();
        return HttpStatus.OK;
    }
}
