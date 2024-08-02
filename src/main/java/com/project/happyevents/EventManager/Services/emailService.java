package com.project.happyevents.EventManager.Services;

import java.util.Map;


public interface emailService {
    public void sendMail();
    public String sendMailWithTemplate(String recipientEmail, String templateName, Map<String, Object> context) throws Exception;
}
