package com.service.api.service.email;

import com.service.api.config.BaseServicesDefaultEmail;
import com.service.api.framework.constants.Regex;
import jakarta.mail.internet.InternetAddress;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

@Service
@Slf4j
public class MailService {
    protected static final String ERROR_SENDING_EMAIL_TO_USER = "Error occurred during sending notification email to ";

    @Value("${base.service.api.mail.enable}")
    private boolean isEnableSendingEmail;

    @Value("${base.service.api.mail.retry.limit}")
    private Integer retryLimit;

    private final JavaMailSender mailSender;

    private final MailContentBuilderService mailContentBuilderService;

    private final BaseServicesDefaultEmail baseServicesDefaultEmail;

    public MailService(JavaMailSender mailSender, MailContentBuilderService mailContentBuilderService, BaseServicesDefaultEmail baseServicesDefaultEmail) {
        this.mailSender = mailSender;
        this.mailContentBuilderService = mailContentBuilderService;
        this.baseServicesDefaultEmail = baseServicesDefaultEmail;
    }

    public boolean sendEmail(String[] recipients, String[] mailCCList, String subject, String templateUrl, Map<String, Object> inputs) {

        if (!isEnableSendingEmail) return false;

        String[] mailTo = getMailTo(recipients);
        String[] ccEmails = getMailCC(mailCCList);

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setFrom(new InternetAddress(baseServicesDefaultEmail.getFrom(), baseServicesDefaultEmail.getFromAlias()));
            messageHelper.setTo(mailTo);
            messageHelper.setCc(ccEmails);
            messageHelper.setSubject(subject);
            messageHelper.setText(mailContentBuilderService.buildMailContent(templateUrl, inputs), true);
        };
        boolean isSent = false;
        int retryCount = 0;
        while (!isSent && retryCount <= retryLimit) {
            if (retryCount > 0) {
                log.debug(String.format("Retrying... [Attempt %d/%d]", retryCount, retryLimit));
            }
            try {
                mailSender.send(messagePreparator);
                log.info("Successfully send email to " + Arrays.toString(recipients).replaceAll(Regex.SQUARE_BRACKETS, Regex.EMPTY_STRING));
                isSent = true;
            } catch (Exception ex) {
                log.error(ERROR_SENDING_EMAIL_TO_USER + Arrays.toString(recipients).replaceAll(Regex.SQUARE_BRACKETS, Regex.EMPTY_STRING), ex);
                retryCount++;
            }
        }
        return isSent;
    }

    private String[] getMailTo(String[] mailTo) {
        return baseServicesDefaultEmail.isEnableTestingMode() ? baseServicesDefaultEmail
                .getEmailTo().stream().map(BaseServicesDefaultEmail.InternalServicesEmail::getAddress).toArray(String[]::new) : mailTo;
    }

    private String[] getMailCC(String... mailCCList) {

        return baseServicesDefaultEmail.isEnableTestingMode() ? baseServicesDefaultEmail
                .getEmailCCList().stream().map(BaseServicesDefaultEmail.InternalServicesEmail::getAddress)
                .toArray(String[]::new) : mailCCList;

    }
}
