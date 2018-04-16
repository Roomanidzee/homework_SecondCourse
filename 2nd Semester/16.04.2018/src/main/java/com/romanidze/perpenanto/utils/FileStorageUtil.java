package com.romanidze.perpenanto.utils;

import com.itextpdf.text.pdf.BaseFont;
import com.romanidze.perpenanto.domain.user.FileInfo;
import com.romanidze.perpenanto.domain.user.Product;
import com.romanidze.perpenanto.repositories.ProfileRepository;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * 07.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Component
public class FileStorageUtil {

    private final ProfileRepository profileRepository;

    @Value("${storage.path}")
    private String storagePath;

    @Autowired
    public FileStorageUtil(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public String getStoragePath() {
        return storagePath;
    }

    private String getURLOfFile(String storageFileName){

        StringBuilder sb = new StringBuilder();
        sb.append(this.storagePath).append("\\").append(storageFileName);
        return sb.toString();

    }

    private String createFileStorageName(String originalFileName){

        String extension = FilenameUtils.getExtension(originalFileName);
        String newFileName = UUID.randomUUID().toString();

        StringBuilder sb = new StringBuilder();
        sb.append(newFileName).append(".").append(extension);
        return sb.toString();

    }

    @SneakyThrows
    public void copyToStorage(MultipartFile file, String storageFileName){
        Files.copy(file.getInputStream(), Paths.get(this.storagePath, storageFileName));
    }

    public FileInfo convertFromMultipart(MultipartFile file){

        String originalFileName = file.getOriginalFilename();
        String type = file.getContentType();
        long size = file.getSize();
        String storageFileName = this.createFileStorageName(originalFileName);
        String fileURL = this.getURLOfFile(storageFileName);

        return FileInfo.builder()
                .originalFileName(originalFileName)
                .storageFileName(storageFileName)
                .url(fileURL)
                .size(size)
                .type(type)
                .build();

    }

    @SneakyThrows
    public FileInfo convertHTMLToPDF(Long id, List<Product> products, Integer price, Timestamp timestamp){

        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("XHTML");
        templateResolver.setCharacterEncoding("UTF-8");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("products", products);
        context.setVariable("price", price);
        context.setVariable("timestamp", timestamp);

        String html = templateEngine.process("email/order_quittance", context);

        StringBuilder sb = new StringBuilder();
        sb.append("reservation#").append(id).append(".pdf");
        String filename = this.createFileStorageName(sb.toString());
        String fileURL = this.getURLOfFile(filename);

        ITextRenderer renderer = new ITextRenderer();
        ITextFontResolver fontResolver = renderer.getFontResolver();

        ClassPathResource timesNewRoman = new ClassPathResource(
                "/static/fonts/TimesNewRomanRegular/TimesNewRomanRegular.ttf"
        );
        fontResolver.addFont(timesNewRoman.getURL().toString(), BaseFont.IDENTITY_H, true);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(bos);

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        Path filePath = Paths.get(fileURL);
        Files.copy(bis, filePath);

        long size = Files.size(filePath);
        String type = "application/pdf";

        return FileInfo.builder()
                       .originalFileName(sb.toString())
                       .storageFileName(filename)
                       .url(fileURL)
                       .size(size)
                       .type(type)
                       .build();
    }

}
