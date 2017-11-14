package com.romanidze.ajaxexample.workwithfiles;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletContext;
import java.io.InputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.util.List;
import java.nio.file.Files;
import java.util.stream.Collectors;

/**
 * 14.11.2017
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public class FileWork {

    private final ServletContext ctx;
    private final String filePath;

    public FileWork(ServletContext ctx, String filePath){
        this.ctx = ctx;
        this.filePath = filePath;
    }

    public JSONObject getJSON(){

        JSONArray dataJSON = new JSONArray();

        InputStream is = this.ctx.getResourceAsStream(this.filePath);
        File tempFile = null;
        FileOutputStream fos = null;
        List<String> dataList = null;

        try {

            tempFile = File.createTempFile("/WEB-INF/temp", "txt");
            tempFile.deleteOnExit();
            fos = new FileOutputStream(tempFile);

            byte[] buffer = new byte[1024];
            int read;

            while((read = is.read(buffer)) != -1){
                 fos.write(buffer, 0, read);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try(BufferedReader br = Files.newBufferedReader(tempFile.toPath(), Charset.forName("UTF-8"))){

            dataList = br.lines()
                         .collect(Collectors.toList());

            JSONObject jsonObject = null;

            for(int i = 0; i < dataList.size(); i++){

                jsonObject = new JSONObject();

                jsonObject.put(i, dataList.get(i));

                dataJSON.add(jsonObject);

                jsonObject.clear();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject result = new JSONObject();
        result.put("carData",dataJSON);

        return result;

    }

}
