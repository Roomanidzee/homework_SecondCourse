package com.romanidze.perpenanto.services.implementations.admin;

import com.romanidze.perpenanto.domain.user.Profile;
import com.romanidze.perpenanto.domain.user.User;
import com.romanidze.perpenanto.repositories.ProfileRepository;
import com.romanidze.perpenanto.repositories.UserRepository;
import com.romanidze.perpenanto.services.interfaces.admin.AdminService;
import com.romanidze.perpenanto.services.interfaces.newsletter.EmailService;
import com.romanidze.perpenanto.utils.PasswordGenerator;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 09.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AdminServiceImpl implements AdminService{

    private UserRepository userRepository;
    private ProfileRepository profileRepository;
    private PasswordGenerator passwordGenerator;
    private PasswordEncoder passwordEncoder;
    private EmailService emailService;
    private TemplateEngine templateEngine;

    @Override
    @Transactional
    public void createTempPassword(Long userId) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        Optional<User> existedUser =
                Optional.of(this.userRepository.findById(userId)
                        .orElseThrow(() -> new IllegalArgumentException("User not found")));

        User admin = this.userRepository.findOne(Long.valueOf(1));
        User user = existedUser.get();

        String tempPassword = this.passwordGenerator.generate();
        user.setHashTempPassword(this.passwordEncoder.encode(tempPassword));
        this.userRepository.save(user);

        executorService.submit(() -> {

            Context context = new Context();
            context.setVariable("message", "Пароль: " + tempPassword);

            ZoneId moscowZone = ZoneId.of("Europe/Moscow");
            LocalDateTime localDateTime = LocalDateTime.now(moscowZone);
            context.setVariable("time", localDateTime.toString());

            String text = this.templateEngine.process("email/mail_template", context);
            this.emailService.sendMail(text,
                    "Сгенерированный пароль для пользователя " + user.getProfile().getEmail(),
                    admin.getProfile().getEmail());

        });

    }

    @Override
    @Transactional
    @SneakyThrows
    public void generateHash(User user) {

        int port = 8080;
        ExecutorService executorService = Executors.newCachedThreadPool();

        ZoneId moscowZone = ZoneId.of("Europe/Moscow");
        LocalDateTime localDateTime = LocalDateTime.now(moscowZone);
        Profile profile = this.profileRepository.findByUserId(user.getId());

        StringBuilder sb = new StringBuilder();
        sb.append(user.getLogin()).append(user.getProtectedPassword())
          .append(user.getState()).append(localDateTime.toString());

        byte[] bytes = sb.toString().getBytes("UTF-8");
        UUID uuid = UUID.nameUUIDFromBytes(bytes);
        String result = uuid.toString();

        user.setConfirmHash(result);
        this.userRepository.save(user);

        StringBuilder sb1 = new StringBuilder();
        String address = InetAddress.getLocalHost().getHostAddress();
        sb1.append(address).append(":").append(port).append("/confirm/").append(result);

        executorService.submit(() -> {

            Context context = new Context();
            context.setVariable("message", "Ссылка для подтверждения: " + sb1.toString());
            context.setVariable("time", localDateTime.toString());

            String text = this.templateEngine.process("email/mail_template", context);
            this.emailService.sendMail(text, "Подтверждение для пользователя " + profile.getEmail(),
                                       profile.getEmail());

        });

    }
}
