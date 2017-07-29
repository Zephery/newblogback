package com.myblog.service;

import com.myblog.model.Blog;
import com.myblog.model.Tag;

import java.util.List;

/**
 * Created by Zephery on 2017/6/20.
 */
public interface ITagService {
    public Integer getBlogByTid(Integer tId);
    public List<Tag> getAllTags();
}
