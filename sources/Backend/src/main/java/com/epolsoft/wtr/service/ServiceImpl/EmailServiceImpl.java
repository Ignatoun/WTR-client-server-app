package com.epolsoft.wtr.service.ServiceImpl;

import com.epolsoft.wtr.dao.UserDAO;
import com.epolsoft.wtr.model.User;
import com.epolsoft.wtr.service.EmailService;
import com.epolsoft.wtr.service.ReportDetailsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.*;

@Service
@Transactional
public class EmailServiceImpl implements EmailService {


    private JavaMailSender javaMailSender;

    private static final Logger LOGGER = LogManager.getLogger(EmailServiceImpl.class);

    private final ReportDetailsService reportDetailsService;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, ReportDetailsService reportDetailsService){
        this.javaMailSender=javaMailSender;
        this.reportDetailsService = reportDetailsService;
    }

    public void sendNotification() throws MailException, MessagingException {

        LOGGER.info("Method to find user by username for sending email");
        Iterable<User> userList = userDAO.findAll();
        List<User> users = new ArrayList<>();
        userList.forEach(users::add);

        Date currentDay=new Date();
        Calendar currentDate = new GregorianCalendar();
        currentDate.setTime(currentDay);

        List<User> usersWithoutReports=new ArrayList<>();

        users.forEach(user -> {
            if(reportDetailsService.getAllDatesWithoutReports(user.getUserId(),currentDate.get(Calendar.MONTH)+1,currentDate.get(Calendar.YEAR)).size()!=0)
                usersWithoutReports.add(user);
        });

        usersWithoutReports.forEach(user -> {
            try {
                MimeMessage mail = javaMailSender.createMimeMessage();
                MimeMessageHelper helper= new MimeMessageHelper(mail,"utf-8");
                helper.setTo(user.getEmail());
                helper.setFrom("checkmailforsend@gmail.com");
                helper.setSubject("Notification");
                helper.setText("Dear "+user.getFirstName()+" "+user.getLastName()+
                        " you need to fill some reports for this month\n" +
                        "<a href=\"google.com\">click me</a>",true);
                javaMailSender.send(mail);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });
    }
}
