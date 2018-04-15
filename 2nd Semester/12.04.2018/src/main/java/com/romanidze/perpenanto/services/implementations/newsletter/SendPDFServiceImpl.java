package com.romanidze.perpenanto.services.implementations.newsletter;

import com.romanidze.perpenanto.domain.user.FileInfo;
import com.romanidze.perpenanto.domain.user.Product;
import com.romanidze.perpenanto.domain.user.Profile;
import com.romanidze.perpenanto.domain.user.User;
import com.romanidze.perpenanto.services.interfaces.newsletter.SendPDFService;
import com.romanidze.perpenanto.services.interfaces.user.ProfileService;
import com.romanidze.perpenanto.services.interfaces.user.ReservationService;
import com.romanidze.perpenanto.utils.FileStorageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 15.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service
public class SendPDFServiceImpl implements SendPDFService{

    private final JavaMailSender javaMailSender;
    private final FileStorageUtil fileStorageUtil;
    private final ProfileService profileService;
    private final ReservationService reservationService;

    @Autowired
    public SendPDFServiceImpl(JavaMailSender javaMailSender, FileStorageUtil fileStorageUtil, ProfileService profileService,
                              ReservationService reservationService) {
        this.javaMailSender = javaMailSender;
        this.fileStorageUtil = fileStorageUtil;
        this.profileService = profileService;
        this.reservationService = reservationService;
    }

    @Override
    @Transactional
    public void sendPDF(FileInfo fileInfo, Profile profile) {

        MimeMessagePreparator messagePreparator = mimeMessage -> {

            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom("perpenantonewsletter@gmail.com");
            messageHelper.setTo(profile.getEmail());
            messageHelper.setSubject("Информация о вашем заказе");

            StringBuilder message = new StringBuilder();
            message.append(profile.getPersonSurname()).append(" ").append(profile.getPersonName())
                    .append(", отправляем вам квитанцию о вашем последнем заказе");
            messageHelper.setText(message.toString(), false);

            FileSystemResource file = new FileSystemResource(fileInfo.getUrl());
            messageHelper.addAttachment(file.getFilename(), file);

        };

        try{
            this.javaMailSender.send(messagePreparator);
        }catch (MailException e) {
            throw new IllegalArgumentException(e);
        }

    }

    @Override
    public void sendEmailWithPDF(User user, Timestamp timestamp) {

        Long id = this.reservationService.getReservationId(timestamp);
        List<Product> products = this.reservationService.getReservationProducts(user.getProfile(), timestamp);
        Integer price = this.reservationService.getReservationPrice(timestamp);
        FileInfo fileInfo = this.fileStorageUtil.convertHTMLToPDF(id, products, price, timestamp);

        user.getProfile().getFiles().add(fileInfo);
        this.profileService.saveOrUpdate(user.getProfile());

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(() -> sendPDF(fileInfo, user.getProfile()));

    }
}
