package com.epolsoft.wtr.service.ServiceImpl;

import com.epolsoft.wtr.dao.*;
import com.epolsoft.wtr.model.Enums.Status;
import com.epolsoft.wtr.model.ReportDetails;
import com.epolsoft.wtr.model.User;
import com.epolsoft.wtr.model.dto.ReportDetailsDTO;
import com.epolsoft.wtr.service.ReportDetailsService;
import com.epolsoft.wtr.util.ResourceNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class ReportDetailsServiceImpl implements ReportDetailsService {

    private static final Logger LOGGER = LogManager.getLogger(ReportDetailsServiceImpl.class);

    private final ReportDetailsDAO reportDetailsDAO;

    @Autowired
    public ReportDetailsServiceImpl(ReportDetailsDAO reportDetailsDAO) {
        this.reportDetailsDAO = reportDetailsDAO;
    }

    @Autowired
    private LocationDAO locationDAO;

    @Autowired
    private FactorDAO factorDAO;

    @Autowired
    private DetailedTaskDAO detailedTaskDAO;

    @Autowired
    private TaskDAO taskDAO;

    @Autowired
    private FeatureDAO featureDAO;

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private UserDAO userDAO;

    @Override
    public List<ReportDetails> getAllReportDetails() {
        LOGGER.info("Service method called to view all list of Reports");
        Iterable<ReportDetails> reportDetailsList = reportDetailsDAO.findAll();
        List<ReportDetails> reportDetails = new ArrayList<>();
        reportDetailsList.forEach(reportDetails::add);
        LOGGER.info("Records found: "+reportDetails.size());
        return reportDetails;
        }

    @Override
    public ReportDetails getReportDetailsById(Integer reportDetailsId) {
        LOGGER.info("Service method called to view Report by id="+reportDetailsId);
        Optional<ReportDetails> reportDetailsOptional = reportDetailsDAO.findById(reportDetailsId);
        if(reportDetailsOptional.isPresent()) {
            LOGGER.info("Report found: " +reportDetailsOptional.get().toString());
            return reportDetailsOptional.get();
        } else {
            LOGGER.info("Report not found");
            LOGGER.error("Report not found");
            throw new ResourceNotFoundException("No reportDetails record exist for given id");
        }
    }

    @Override
    public ReportDetails getReportDetailsByIdToUser(Integer reportDetailsId, Integer userId) {
        LOGGER.info("Service method called to view Report" +
                " by id="+reportDetailsId+
                ", to userId="+userId);

        AtomicReference<ReportDetails> reportDetails= new AtomicReference<>(new ReportDetails());

        reportDetailsDAO.findAllReportsByFilter(userId).forEach(report->{
            if (report.getReportDetailsId()==reportDetailsId)
                reportDetails.set(report);
        });

        if(reportDetails.get().getReportDetailsId()!=null) {
            LOGGER.info("Report found: " +reportDetails.get().toString());
            return reportDetails.get();
        } else {
            LOGGER.info("Report not found");
            LOGGER.error("Report not found");
            throw new ResourceNotFoundException("No reportDetails record exist for given id");
        }
    }

    @Override
    public ReportDetails createReportDetails(ReportDetailsDTO reportDetailsDto) {
        LOGGER.info("Service method called to create Report: "+reportDetailsDto.toString());
        List<Integer> usersId = reportDetailsDto.getUsersId().stream().collect(Collectors.toList());

        Set<User> users=new HashSet<>();

        usersId.forEach(uId->
        {
            users.add(userDAO.findById(uId).get());
        });

        ReportDetails reportDetails=new ReportDetails(
                null,
                reportDetailsDto.getReportDetailsDate(),
                reportDetailsDto.getStatus(),
                factorDAO.findById(reportDetailsDto.getFactorId()).get(),
                locationDAO.findById(reportDetailsDto.getLocationId()).get(),
                reportDetailsDto.getHours(),
                reportDetailsDto.getWorkUnits(),
                detailedTaskDAO.findById(reportDetailsDto.getDetailedTaskId()).get(),
                taskDAO.findById(reportDetailsDto.getTaskId()).get(),
                featureDAO.findById(reportDetailsDto.getFeatureId()).get(),
                projectDAO.findById(reportDetailsDto.getProjectId()).get(),
                reportDetailsDto.getComments(),
                users);

        users.forEach(user ->
        {
            user.getReportDetails().add(reportDetails);
            userDAO.save(user);
        });

        return reportDetailsDAO.save(reportDetails);
    }

    @Override
    public List<ReportDetails> createListOfReportDetails(List<ReportDetailsDTO> reportDetailsDTOList){

        List<Integer> usersId = new ArrayList<>();
        reportDetailsDTOList.forEach(
                reportDetailsDTO -> usersId.addAll(new ArrayList<>(reportDetailsDTO.getUsersId()))
        );

        Set<User> users = new HashSet<>();
        usersId.forEach(uId->
        {
            users.add(userDAO.findById(uId).get());
        });

        List<ReportDetails> reportDetailsList = new ArrayList<>();

        for (ReportDetailsDTO reportDetailsDTO : reportDetailsDTOList) {
            LOGGER.info("Service method called to create Report: " + reportDetailsDTO.toString());

            ReportDetails reportDetails = new ReportDetails(
                    null,
                    reportDetailsDTO.getReportDetailsDate(),
                    reportDetailsDTO.getStatus(),
                    factorDAO.findById(reportDetailsDTO.getFactorId()).get(),
                    locationDAO.findById(reportDetailsDTO.getLocationId()).get(),
                    reportDetailsDTO.getHours(),
                    reportDetailsDTO.getWorkUnits(),
                    detailedTaskDAO.findById(reportDetailsDTO.getDetailedTaskId()).get(),
                    taskDAO.findById(reportDetailsDTO.getTaskId()).get(),
                    featureDAO.findById(reportDetailsDTO.getFeatureId()).get(),
                    projectDAO.findById(reportDetailsDTO.getProjectId()).get(),
                    reportDetailsDTO.getComments(),
                    users);
            reportDetailsList.add(reportDetails);
        }
        return reportDetailsDAO.saveAll(reportDetailsList);
    }

    @Override
    public ReportDetails updateReportDetails(ReportDetailsDTO reportDetailsDto) {
        LOGGER.info("Service method called to update Report;" +
                " report="+reportDetailsDto.toString());
        Optional<ReportDetails> reportDetailsOptional = reportDetailsDAO.findById(reportDetailsDto.getReportDetailsId());

        if(reportDetailsOptional.isPresent())
        {
            LOGGER.info("Report found");
            ReportDetails newReportDetails = reportDetailsOptional.get();
            newReportDetails.setReportDetailsDate(reportDetailsDto.getReportDetailsDate());
            newReportDetails.setComments(reportDetailsDto.getComments());
            newReportDetails.setDetailedTask(detailedTaskDAO.findById(reportDetailsDto.getDetailedTaskId()).get());
            newReportDetails.setFactor(factorDAO.findById(reportDetailsDto.getFactorId()).get());
            newReportDetails.setFeature(featureDAO.findById(reportDetailsDto.getFeatureId()).get());
            newReportDetails.setHours(reportDetailsDto.getHours());
            newReportDetails.setLocation(locationDAO.findById(reportDetailsDto.getLocationId()).get());
            newReportDetails.setProject(projectDAO.findById(reportDetailsDto.getProjectId()).get());
            newReportDetails.setStatus(reportDetailsDto.getStatus());
            newReportDetails.setTask(taskDAO.findById(reportDetailsDto.getTaskId()).get());
            newReportDetails.setWorkUnits(reportDetailsDto.getWorkUnits());

            LOGGER.info("Report save;" +
                    " report="+newReportDetails.toString());
            newReportDetails = reportDetailsDAO.save(newReportDetails);
            return newReportDetails;
        } else {
            LOGGER.info("Report not found");
            LOGGER.error("Report not found");
            throw new ResourceNotFoundException("No reportDetails record exist for given id");
        }
    }

    @Override
    public List<ReportDetails> updateListOfReportDetails(List<ReportDetailsDTO> reportDetailsDtoList) {
        List<Optional<ReportDetails>> reportDetailsOptionalList = new ArrayList<>();
        List<ReportDetails> updatedReports = new ArrayList<>();
        for (ReportDetailsDTO reportDetailsDTO : reportDetailsDtoList) {
            reportDetailsOptionalList.add(reportDetailsDAO.findById(reportDetailsDTO.getReportDetailsId()));
        }

        for (int i = 0; i < reportDetailsDtoList.size(); i++) {
            if (reportDetailsOptionalList.get(i).isPresent()) {
                LOGGER.info("Service method called to update Report;" +
                        " report=" + reportDetailsDtoList.get(i).toString());
                LOGGER.info("Report found");
                ReportDetails newReportDetails = reportDetailsOptionalList.get(i).get();
                newReportDetails.setReportDetailsDate(reportDetailsDtoList.get(i).getReportDetailsDate());
                newReportDetails.setComments(reportDetailsDtoList.get(i).getComments());
                newReportDetails.setDetailedTask(detailedTaskDAO.findById(reportDetailsDtoList.get(i).getDetailedTaskId()).get());
                newReportDetails.setFactor(factorDAO.findById(reportDetailsDtoList.get(i).getFactorId()).get());
                newReportDetails.setFeature(featureDAO.findById(reportDetailsDtoList.get(i).getFeatureId()).get());
                newReportDetails.setHours(reportDetailsDtoList.get(i).getHours());
                newReportDetails.setLocation(locationDAO.findById(reportDetailsDtoList.get(i).getLocationId()).get());
                newReportDetails.setProject(projectDAO.findById(reportDetailsDtoList.get(i).getProjectId()).get());
                newReportDetails.setStatus(reportDetailsDtoList.get(i).getStatus());
                newReportDetails.setTask(taskDAO.findById(reportDetailsDtoList.get(i).getTaskId()).get());
                newReportDetails.setWorkUnits(reportDetailsDtoList.get(i).getWorkUnits());

                updatedReports.add(newReportDetails);
            } else {
                LOGGER.info("Report not found");
                LOGGER.error("Report not found");
                throw new ResourceNotFoundException("No reportDetails record exist for given id");
            }
        }
        return reportDetailsDAO.saveAll(updatedReports);
    }

    @Override
    public List<ReportDetails> getAllReportDetailsByDate(Date date) {
        LOGGER.info("Service method called to view all list of Reports by date" +
                " date="+ date.toString());
        List<ReportDetails> reportDetails = reportDetailsDAO.findAllByReportDetailsDate(date);
        if (!reportDetails.isEmpty()) {
            LOGGER.info("Records found: "+reportDetails.size());
            return reportDetails;
        }
        else {
            LOGGER.info("Reports not found");
            LOGGER.error("Reports not found");
            throw new ResourceNotFoundException("No list of reportDetails record exist for given date");
        }
    }

    @Override
    public List<ReportDetails> getAllReportDetailsByDateAndUser(String date, Integer userId) {
        LOGGER.info("Service method called to view all list of Reports by date and userId;" +
                " date="+ date+", userId="+userId);
        List<ReportDetails> reportDetails = reportDetailsDAO.findAllReportsByUserIdAndDate(userId,date);
        if (!reportDetails.isEmpty()) {
            LOGGER.info("Records found: "+reportDetails.size());
            return reportDetails;
        }
        else {
            LOGGER.info("Reports not found");
            LOGGER.error("Reports not found");
            throw new ResourceNotFoundException("No list of reportDetails record exist for given date");
        }
    }
    @Override
    public List<ReportDetails> getAllReportDetailsByBetweenOfDates(Date dateStart, Date dateEnd) {
        LOGGER.info("Service method called to view all list of Reports by start date and end date;" +
                " startDate="+ dateStart.toString()+", endDate="+dateEnd.toString());
        List<ReportDetails> reportDetails = reportDetailsDAO.findAllByReportDetailsDateBetween(dateStart,dateEnd);
        if (!reportDetails.isEmpty()) {
            LOGGER.info("Records found: "+reportDetails.size());
            return reportDetails;
        }
        else {
            LOGGER.info("Reports not found");
            LOGGER.error("Reports not found");
            throw new ResourceNotFoundException("No list of reportDetails record exist for given dates");
        }
    }
    @Override
    public void deleteReportDetails(Integer reportDetailsId) {
        LOGGER.info("Service method called to delete Report by id="+reportDetailsId);
        Optional<ReportDetails> reportDetails = reportDetailsDAO.findById(reportDetailsId);
        if(reportDetails.isPresent()){
            LOGGER.info("Report found: " +reportDetails.get().toString());
            reportDetailsDAO.deleteById(reportDetailsId);
        } else {
            LOGGER.info("Report not found");
            LOGGER.error("Report not found");
            throw new ResourceNotFoundException("No reportDetails record exist for given id");
        }
    }
    @Override
    public void deleteAllReportDetails() {
        LOGGER.info("Service method called to delete all Reports");
        List<ReportDetails> reportDetails = reportDetailsDAO.findAll();
        if(!reportDetails.isEmpty()){
            LOGGER.info("Records found: "+reportDetails.size());
            reportDetailsDAO.deleteAll();
        }
        else {
            LOGGER.info("Reports not found");
            LOGGER.error("Reports not found");
            throw new ResourceNotFoundException("There is no list of reportDetails entries.");
        }
    }

    @Override
    public List<Integer> getAllDatesWithoutReports(Integer userId, Integer month, Integer year)
    {
        LOGGER.info("Service method called to view all days without Reports;" +
                " userId="+userId+", month="+month+", year="+year);

        List<ReportDetails> reportDetailsList=reportDetailsDAO.findAllReportsByUserIdAndMonth(userId,month,year);

        LOGGER.info("Records found: "+reportDetailsList.size());

        Boolean cur=false,last=false,next=false;
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month-1);
        Integer count_days=c.getActualMaximum(Calendar.DAY_OF_MONTH);

        Date currentDay=new Date();
        Calendar currentDate = new GregorianCalendar();
        currentDate.setTime(currentDay);

        c.set(Calendar.DAY_OF_MONTH,currentDate.get(Calendar.DAY_OF_MONTH));
        currentDate.set(Calendar.HOUR_OF_DAY,0);
        c.set(Calendar.HOUR_OF_DAY,0);
        currentDate.set(Calendar.MINUTE,0);
        c.set(Calendar.MINUTE,0);
        currentDate.set(Calendar.SECOND,0);
        c.set(Calendar.SECOND,0);
        currentDate.set(Calendar.MILLISECOND,0);
        c.set(Calendar.MILLISECOND,0);

        if(currentDate.equals(c))
            cur=true;
        else if(currentDate.before(c))
            next=true;
        else if(currentDate.after(c))
            last=true;


        final List<Integer> DaysWithoutReports=new ArrayList<>();
        List<Integer> listDays=new ArrayList<>();

        for(int i=1;i<=count_days;i++){
            boolean flag = true, reject=false;
            for(int j =0;j<reportDetailsList.size();j++){
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Minsk"));
                cal.setTime(reportDetailsList.get(j).getReportDetailsDate());
                if(cal.get(Calendar.DAY_OF_MONTH)== i){
                    if(reportDetailsList.get(j).getStatus()==Status.REGISTERED ||
                            reportDetailsList.get(j).getStatus()==Status.APPROVED)
                        flag=false;
                    if(reportDetailsList.get(j).getStatus()==Status.REJECTED ||
                            reportDetailsList.get(j).getStatus()==null||
                            reportDetailsList.get(j).getStatus()==Status.PRIVATE)
                        reject=true;
                }
            }
            if(flag||reject)
                listDays.add(i);
        }



        if(cur.equals(true)){
            listDays.forEach(day->{
                if(day<=currentDate.get(Calendar.DAY_OF_MONTH))
                    DaysWithoutReports.add(day);
            });
        }

        else if(next.equals(true))
            DaysWithoutReports.clear();

        else if(last.equals(true))
            listDays.forEach(day->
                    DaysWithoutReports.add(day));

        LOGGER.info("Days without Reports: "+DaysWithoutReports.size());

        return DaysWithoutReports;
    }

    public List<ReportDetails> getReportsByFilter(Integer userId, Status status,
                                                  Integer projectId, Integer taskId,
                                                  Integer detailedTaskId, Integer featureId,
                                                  Integer factorId, Integer locationId,
                                                  Date dateStart , Date dateEnd)
    {
        LOGGER.info("Service method called to view all Reports of user="+userId);
        List<ReportDetails> reportDetailsList = reportDetailsDAO.findAllReportsByFilter(userId);

        List<ReportDetails> reportDetailsListCopy = new ArrayList();
        reportDetailsListCopy.addAll(reportDetailsList);

        reportDetailsListCopy.forEach(rep -> {

            if (status!=null)
            {
                if(rep.getStatus()!=null){
                    if(rep.getStatus()!=status)
                        reportDetailsList.remove(rep);
                }
                else reportDetailsList.remove(rep);
            }

            if (projectId!=null)
            {
                if(rep.getProject()!=null){
                    if(rep.getProject().getProjectId()!=projectId)
                        reportDetailsList.remove(rep);
                }
                else reportDetailsList.remove(rep);
            }

            if (taskId!=null)
            {
                if(rep.getTask()!=null)
                {
                    if(rep.getTask().getTaskId()!=taskId)
                        reportDetailsList.remove(rep);
                }
                else reportDetailsList.remove(rep);
            }

            if (detailedTaskId!=null)
            {
                if(rep.getDetailedTask()!=null)
                {
                    if(rep.getDetailedTask().getDetailedTaskId()!=detailedTaskId)
                        reportDetailsList.remove(rep);
                }
                else reportDetailsList.remove(rep);
            }

            if(featureId!=null)
            {
                if(rep.getFeature()!=null)
                {
                    if(rep.getFeature().getFeatureId()!=featureId)
                        reportDetailsList.remove(rep);
                }
                else reportDetailsList.remove(rep);
            }

            if(factorId!=null)
            {
                if(rep.getFactor()!=null)
                {
                    if(rep.getFactor().getFactorId()!=factorId)
                        reportDetailsList.remove(rep);
                }
                else reportDetailsList.remove(rep);
            }

            if(locationId!=null)
            {
                if(rep.getLocation()!=null)
                {
                    if(rep.getLocation().getLocationId()!=locationId)
                        reportDetailsList.remove(rep);
                }
                else reportDetailsList.remove(rep);
            }

            if(dateStart!=null || dateEnd!=null){
                if(dateStart!=null && dateEnd !=null) {
                    if (dateStart.compareTo(rep.getReportDetailsDate()) > 0 || dateEnd.compareTo(rep.getReportDetailsDate()) < 0)
                        reportDetailsList.remove(rep);
                }else if(dateStart!=null && dateEnd ==null){
                    if (dateStart.compareTo(rep.getReportDetailsDate()) > 0)
                        reportDetailsList.remove(rep);
                }else if(dateStart==null && dateEnd !=null){
                    if (dateEnd.compareTo(rep.getReportDetailsDate()) < 0)
                        reportDetailsList.remove(rep);
                }
            }
        });

        LOGGER.info("Records with Reports by filter:"+reportDetailsList.size());
        return reportDetailsList;

    }

    public List<ReportDetails> getReportsByFilterWithoutPrivate(Integer userId, Status status,
                                                  Integer projectId, Integer taskId,
                                                  Integer detailedTaskId, Integer featureId,
                                                  Integer factorId, Integer locationId,
                                                  Date dateStart , Date dateEnd){
        LOGGER.info("Service method called to view all Reports without Private of user="+userId);
        List<ReportDetails> reportDetailsList = reportDetailsDAO.findAllReportsByFilter(userId);

        List<ReportDetails> reportDetailsListCopy = new ArrayList();
        reportDetailsListCopy.addAll(reportDetailsList);

        reportDetailsListCopy.forEach(rep -> {

            if(rep.getStatus()==Status.PRIVATE)
                reportDetailsList.remove(rep);

            if (status!=null){
                if(rep.getStatus()!=null){
                    if(rep.getStatus()!=status)
                        reportDetailsList.remove(rep);
                }
                else reportDetailsList.remove(rep);
            }

            if (projectId!=null){
                if(rep.getProject()!=null){
                    if(rep.getProject().getProjectId()!=projectId)
                        reportDetailsList.remove(rep);
                }
                else reportDetailsList.remove(rep);
            }

            if (taskId!=null){
                if(rep.getTask()!=null){
                    if(rep.getTask().getTaskId()!=taskId)
                        reportDetailsList.remove(rep);
                }
                else reportDetailsList.remove(rep);
            }

            if (detailedTaskId!=null){
                if(rep.getDetailedTask()!=null){
                    if(rep.getDetailedTask().getDetailedTaskId()!=detailedTaskId)
                        reportDetailsList.remove(rep);
                }
                else reportDetailsList.remove(rep);
            }

            if(featureId!=null){
                if(rep.getFeature()!=null){
                    if(rep.getFeature().getFeatureId()!=featureId)
                        reportDetailsList.remove(rep);
                }
                else reportDetailsList.remove(rep);
            }

            if(factorId!=null){
                if(rep.getFactor()!=null){
                    if(rep.getFactor().getFactorId()!=factorId)
                        reportDetailsList.remove(rep);
                }
                else reportDetailsList.remove(rep);
            }

            if(locationId!=null){
                if(rep.getLocation()!=null){
                    if(rep.getLocation().getLocationId()!=locationId)
                        reportDetailsList.remove(rep);
                }
                else reportDetailsList.remove(rep);
            }

            if(dateStart!=null || dateEnd!=null){
                if(dateStart!=null && dateEnd !=null) {
                    if (dateStart.compareTo(rep.getReportDetailsDate()) > 0 || dateEnd.compareTo(rep.getReportDetailsDate()) < 0)
                        reportDetailsList.remove(rep);
                }else if(dateStart!=null && dateEnd ==null){
                    if (dateStart.compareTo(rep.getReportDetailsDate()) > 0)
                        reportDetailsList.remove(rep);
                }else if(dateStart==null && dateEnd !=null){
                    if (dateEnd.compareTo(rep.getReportDetailsDate()) < 0)
                        reportDetailsList.remove(rep);
                }
            }
        });

        LOGGER.info("Records with Reports by filter:"+reportDetailsList.size());
        return reportDetailsList;

    }

    @Override
    public List<ReportDetails> getAllReportsByFilter(Status status,
                                                     Integer projectId,
                                                     Integer taskId,
                                                     Integer detailedTaskId,
                                                     Integer featureId,
                                                     Integer factorId,
                                                     Integer locationId,
                                                     Date dateStart, Date dateEnd) {
        LOGGER.info("Service method called to view all Reports");

        Iterable<User> userList = userDAO.findAll();
        List<User> users = new ArrayList<>();
        userList.forEach(users::add);

        List<ReportDetails> reportDetailsList= new ArrayList<>();

        users.forEach(user -> {
            reportDetailsList.addAll(reportDetailsDAO.findAllReportsByFilter(user.getUserId()));
        });

        List<ReportDetails> reportDetailsListCopy = new ArrayList();
        reportDetailsListCopy.addAll(reportDetailsList);

        reportDetailsListCopy.forEach(rep -> {
            if(rep.getStatus()==Status.PRIVATE)
                reportDetailsList.remove(rep);

            if (status!=null){
                if(rep.getStatus()!=null){
                    if(rep.getStatus()!=status)
                        reportDetailsList.remove(rep);
                }
                else reportDetailsList.remove(rep);
            }

            if (projectId!=null){
                if(rep.getProject()!=null){
                    if(rep.getProject().getProjectId()!=projectId)
                        reportDetailsList.remove(rep);
                }
                else reportDetailsList.remove(rep);
            }

            if (taskId!=null){
                if(rep.getTask()!=null){
                    if(rep.getTask().getTaskId()!=taskId)
                        reportDetailsList.remove(rep);
                }
                else reportDetailsList.remove(rep);
            }

            if (detailedTaskId!=null){
                if(rep.getDetailedTask()!=null){
                    if(rep.getDetailedTask().getDetailedTaskId()!=detailedTaskId)
                        reportDetailsList.remove(rep);
                }
                else reportDetailsList.remove(rep);
            }

            if(featureId!=null){
                if(rep.getFeature()!=null){
                    if(rep.getFeature().getFeatureId()!=featureId)
                        reportDetailsList.remove(rep);
                }
                else reportDetailsList.remove(rep);
            }

            if(factorId!=null){
                if(rep.getFactor()!=null){
                    if(rep.getFactor().getFactorId()!=factorId)
                        reportDetailsList.remove(rep);
                }
                else reportDetailsList.remove(rep);
            }

            if(locationId!=null){
                if(rep.getLocation()!=null){
                    if(rep.getLocation().getLocationId()!=locationId)
                        reportDetailsList.remove(rep);
                }
                else reportDetailsList.remove(rep);
            }

            if(dateStart!=null || dateEnd!=null){
                if(dateStart!=null && dateEnd !=null) {
                    if (dateStart.compareTo(rep.getReportDetailsDate()) > 0 || dateEnd.compareTo(rep.getReportDetailsDate()) < 0)
                        reportDetailsList.remove(rep);
                }else if(dateStart!=null && dateEnd ==null){
                    if (dateStart.compareTo(rep.getReportDetailsDate()) > 0)
                        reportDetailsList.remove(rep);
                }else if(dateStart==null && dateEnd !=null){
                    if (dateEnd.compareTo(rep.getReportDetailsDate()) < 0)
                        reportDetailsList.remove(rep);
                }
            }
        });

        LOGGER.info("Records with Reports by filter:"+reportDetailsList.size());
        return reportDetailsList;
    }
}

