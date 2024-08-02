package com.project.happyevents.EventManager.Services.impl;

import com.azure.communication.email.EmailClient;
import com.azure.communication.email.models.EmailMessage;
import com.azure.communication.email.models.EmailSendResult;
import com.azure.core.util.polling.PollResponse;
import com.azure.core.util.polling.SyncPoller;
import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.project.happyevents.EventManager.Services.emailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.Map;

@Service
public class emailServiceImpl implements emailService {

    @Autowired
    private EmailClient emailClient;
    @Autowired
    private PebbleEngine pebbleEngine;

    @Override
    public String sendMailWithTemplate(String recipientEmail, String templateName, Map<String, Object> context) throws Exception {
        PebbleTemplate pebbleTemplate = pebbleEngine.getTemplate("templates/" + templateName + ".peb");

        StringWriter writer = new StringWriter();
        pebbleTemplate.evaluate(writer, context);
        String evaluatedTemplate = writer.toString();

        String[] lines = evaluatedTemplate.split("\n", 2);
        String subject = lines[0].replace("Subject: ", "").trim();
        String body = lines[1].trim();

        EmailMessage mail = new EmailMessage()
                .setSenderAddress("DoNotReply@d5333fcb-9028-4606-867b-84aa47f9b3eb.azurecomm.net")
                .setToRecipients(recipientEmail)
                .setSubject(subject)
                .setBodyPlainText(body);
        SyncPoller<EmailSendResult, EmailSendResult> poller = emailClient.beginSend(mail);
        PollResponse<EmailSendResult> response = poller.waitForCompletion();
        return "ok";}
    @Override
    public void sendMail() {

    }
}
