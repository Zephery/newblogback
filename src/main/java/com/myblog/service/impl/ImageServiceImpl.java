package com.myblog.service.impl;

import com.myblog.dao.ImageMapper;
import com.myblog.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Zephery on 2017/6/18.
 */
@Service("imageService")
public class ImageServiceImpl implements IImageService{
    @Resource
    private ImageMapper imageMapper;
}
