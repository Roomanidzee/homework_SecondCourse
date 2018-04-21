package com.romanidze.perpenanto.services.implementations.newsletter;

import com.romanidze.perpenanto.services.interfaces.newsletter.SMSService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 15.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service
public class SMSServiceImpl implements SMSService{

    private ExecutorService executorService = Executors.newCachedThreadPool();

    @Value("${sms.aero.user}")
    private String smsAeroLogin;

    @Value("${sms.aero.password}")
    private String smsAeroPassword;

    @Value("${sms.aero.from}")
    private String smsAeroFrom;

    @Value("${sms.aero.type}")
    private String smsAeroType;

    @Value("${sms.aero.url}")
    private String smsAeroUri;

    @Override
    @Transactional
    @SneakyThrows
    public boolean sendSMS(String phone, String text) {

        Future<Boolean> result =  this.executorService.submit(() ->{

            RestTemplate restTemplate = new RestTemplate();

            StringBuilder sb = new StringBuilder();

            sb.append(this.smsAeroUri)
              .append("?user=").append(this.smsAeroLogin)
              .append("&password=").append(this.smsAeroPassword)
              .append("&to=").append(phone)
              .append("&text=").append(text)
              .append("&from=").append(this.smsAeroFrom)
              .append("&type=").append(this.smsAeroType);

            ResponseEntity<String> responseEntity = restTemplate.getForEntity(sb.toString(), String.class);

            if (responseEntity.getBody().contains("accepted")) {
                return true;
            } else {
                throw new IllegalArgumentException("Проблемы с номером телефона");
            }

        });

        return result.get();

    }
}
