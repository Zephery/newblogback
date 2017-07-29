package com.myblog.controller;

import com.myblog.model.Bo.RestResponseBo;
import com.myblog.service.IImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
@RequestMapping("admin/attach")
public class ImageController {
    private final static Logger logger = LoggerFactory.getLogger(ImageController.class);
    @Resource
    private IImageService imageService;

    @GetMapping("")
    public String attach(HttpServletRequest request, HttpServletResponse response) {
        return "admin/attach";
    }

    @PostMapping("upload")
    @ResponseBody
    public RestResponseBo upload(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam("file") MultipartFile[] multipartFiles) throws IOException {
        try {
            for (MultipartFile multipartFile : multipartFiles) {
                BufferedImage bufferedImg = ImageIO.read(multipartFile.getInputStream());
                int height = bufferedImg.getHeight();
                System.out.println(height);
            }
        } catch (Exception e) {
            logger.error("file upload error", e);
            return RestResponseBo.fail();
        }
        return RestResponseBo.ok();
    }
}
