package com.epolsoft.wtr.service.ServiceImpl;

import com.epolsoft.wtr.scheduler.SchedulerTask;
import com.epolsoft.wtr.dao.SchedulerDAO;
import com.epolsoft.wtr.model.Scheduler;
import com.epolsoft.wtr.service.SchedulerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class SchedulerServiceImpl implements SchedulerService {

    @Value("${scheduler.cron.value}")
    private String cron;

    private static final Logger LOGGER = LogManager.getLogger(SchedulerServiceImpl.class);

    @Autowired
    private SchedulerDAO schedulerDAO;

    private final SchedulerTask schedulerTask;

    public SchedulerServiceImpl(SchedulerTask schedulerTask) {
        this.schedulerTask = schedulerTask;
    }

    @PostConstruct
    public void init(){

        String cronBuilder="";
        List<Scheduler> schedulerList=schedulerDAO.findAll();

        for(int i=schedulerList.size()-1;i>0;i--){
            if(i!=schedulerList.size()-1)
                cronBuilder+=" "+schedulerList.get(i).getValue();
            else if (i==schedulerList.size()-1)
                cronBuilder+=schedulerList.get(i).getValue();
        }
        cronBuilder+=" ? * "+schedulerList.get(0).getValue();

        cron=cronBuilder;
        schedulerTask.initializeScheduler(cron);
    }

    @Override
    public Scheduler createParam(Scheduler scheduler) {
        LOGGER.info("Service method called to create Parameter;" +
                " parameter="+scheduler.toString());

        return schedulerDAO.save(scheduler);
    }

    @Override
    public Scheduler updateParam(Scheduler scheduler) {
        LOGGER.info("Service method called to update Parameter;" +
                " parameter="+scheduler.toString());
        Scheduler scheduler1=schedulerDAO.save(scheduler);
        init();
        return scheduler1;
    }

    @Override
    public List<Scheduler> updateParams(List<Scheduler> schedulers) {
        LOGGER.info("Service method called to update Parameters");
        List<Scheduler> schedulers1=schedulerDAO.saveAll(schedulers);
        init();
        return schedulers1;
    }

    @Override
    public List<Scheduler> getAllParams() {
        LOGGER.info("Service method called to get All Parameter");
        List<Scheduler> schedulerList = schedulerDAO.findAll();
        LOGGER.info("List size: "+ schedulerList.size());

        return schedulerList;
    }

    @Override
    public Scheduler getParamById(Integer paramId) {
        LOGGER.info("Service method called to get Parameter;" +
                " paramId="+paramId);
        Scheduler scheduler= schedulerDAO.findById(paramId).get();
        LOGGER.info("parameter: "+scheduler.toString() );

        return scheduler;
    }

    @Override
    public void deleteParam(Integer paramId) {
        LOGGER.info("Service method called to delete Parameter;" +
                " paramId="+paramId);

        schedulerDAO.deleteById(paramId);
    }
}
