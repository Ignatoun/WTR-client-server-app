package com.epolsoft.wtr.scheduler;

import com.epolsoft.wtr.service.EmailService;
import lombok.SneakyThrows;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;

@Component
public class SchedulerTask implements Runnable {

    private static Logger LOGGER = LogManager.getLogger(SchedulerTask.class);

    @SuppressWarnings("rawtypes")
    private ScheduledFuture scheduledFuture;

    private TaskScheduler taskScheduler;

    @Autowired
    private EmailService emailService;

    public void reSchedule(String cronExpressionStr) {
        if (taskScheduler == null) {
            this.taskScheduler = new ConcurrentTaskScheduler();
        }
        if (this.scheduledFuture != null) {
            this.scheduledFuture.cancel(true);
        }
        this.scheduledFuture = this.taskScheduler.schedule(this, new CronTrigger(cronExpressionStr));
    }

    @SneakyThrows
    @Override
    public void run() {
        emailService.sendNotification();
    }

    public void initializeScheduler(String cronExpression) {
        LOGGER.info("change cron to: "+cronExpression);
        this.reSchedule(cronExpression);
    }
}
