package com.epolsoft.wtr.service;

import javax.mail.MessagingException;

public interface EmailService {

    void sendNotification() throws MessagingException;
}
