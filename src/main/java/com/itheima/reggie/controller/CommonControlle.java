package com.itheima.reggie.controller;


import ch.qos.logback.core.util.FileUtil;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.itheima.reggie.common.R;
import com.sun.deploy.net.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequestMapping("/common")
public class CommonControlle {

    @Autowired
    ResourceLoader resourceLoader;

    @PostMapping("/upload")
    public R uploadPic(@RequestParam(value = "file")MultipartFile multipartFile,HttpServletRequest request) throws IOException {
        //存储路径
        //this.getClass().getClassLoader().getResourceAsStream("upload/")
        //String name = multipartFile.getOriginalFilename();
        DateTimeFormatter datet = DateTimeFormatter.ofPattern("yyMMdd_HHmmss");
        String name = datet.format(LocalDateTime.now())+".jpg";
        //Resource resource = resourceLoader.getResource("upload/img");
        //获取resources下绝对路径
        ApplicationHome home = new ApplicationHome(this.getClass());
        String path = home.getDir().getParentFile().getParentFile().getAbsolutePath()+"\\src\\main\\resources\\upload\\zimg";
        //String path = resource.getFile().getPath();
        File file = new File(path,name);
        //System.out.println("file.getAbsolutePath() = " + file.getAbsolutePath());
        multipartFile.transferTo(file);
        return R.success(name);
    }

    @GetMapping("/download")
    public R downloadPic(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //图片路径
        String name = request.getParameter("name");
        ApplicationHome home = new ApplicationHome(this.getClass());
        String path = home.getDir().getParentFile().getParentFile().getAbsolutePath()+"\\src\\main\\resources\\upload\\zimg";
        try {
            File file = new File(path,name);
            InputStream is = new FileInputStream(file);
            ServletOutputStream stream = response.getOutputStream();
            byte[] buf = new byte[(int) file.length()];
            is.read(buf,0,buf.length);
            stream.write(buf);
            stream.flush();
            return R.success(stream);
        }catch (FileNotFoundException e){
            return R.error("文件不存在");
        }
    }
}


