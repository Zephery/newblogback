package com.myblog.service.impl;

import com.myblog.dao.BlogMapper;
import com.myblog.dao.CategoryMapper;
import com.myblog.dao.RelationMapper;
import com.myblog.dao.TagMapper;
import com.myblog.model.Blog;
import com.myblog.model.Category;
import com.myblog.model.Relation;
import com.myblog.model.Tag;
import com.myblog.service.IBlogService;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Zephery on 2017/1/18.
 */
@Service("blogService")
public class BlogServiceImpl implements IBlogService {
    @Resource
    private BlogMapper blogMapper;
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private RelationMapper relationMapper;
    @Resource
    private TagMapper tagMapper;
    private static final Logger logger = LoggerFactory.getLogger(BlogServiceImpl.class);
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private static final String IMGSRC_REG = "(http|https)://.*\\.(jpg|png|gif)";

    @Override
    public List<Blog> getAllBlog() {
        List<Blog> blogs = blogMapper.getAllBlog();
        for (Blog blog : blogs) {
            blog.setCategory(categoryMapper.selectByPrimaryKey(blog.getCategoryid()));
            try {
                blog.setCreateAt(df.format(df.parse(blog.getCreateAt())));
            } catch (Exception e) {
                logger.error("转换格式错误", e);
            }
        }
        return blogs;
    }

    @Override
    public List<Blog> getByCategoryId(int categoryid) {
        List<Blog> blogs = blogMapper.getByCategoryId(categoryid);
        for (Blog blog : blogs) {
            try {
                blog.setCreateAt(df.format(df.parse(blog.getCreateAt())));
            } catch (Exception e) {
                logger.error("转换格式错误", e);
            }
        }
        return blogs;
    }

    @Override
    public Blog getBlogDetail(Integer blogid) {
        Blog blog = blogMapper.selectByPrimaryKey(blogid);
        if (blog == null) {
            return null;
        }
        Category category = categoryMapper.selectByPrimaryKey(blog.getCategoryid());
        blog.setCategory(category);
        List<Tag> tags = tagMapper.getTagByBlogId(blog.getBlogid());
        List<String> list = new ArrayList<>();
        for (Tag tag : tags) {
            list.add(tag.gettName());
        }
        blog.setTagforsplit(StringUtils.join(list, ","));
        blog.setTags(tags.size() > 0 ? tags : null);
        if (blogMapper.updatehits(blogid)) {
            logger.info("read count success");
        } else {
            logger.info("read count failure");
        }
        return blog;
    }

    @Override
    public Tag getTagByTid(Integer t_id) {
        return tagMapper.selectByPrimaryKey(t_id);
    }

    @Override
    public List<Blog> getBlogByTagId(Integer tId) {
        List<Blog> blogs = blogMapper.getBlogByTagId(tId);
        for (Blog blog : blogs) {
            blog.setCategory(categoryMapper.selectByPrimaryKey(blog.getCategoryid()));
            try {
                blog.setCreateAt(df.format(df.parse(blog.getCreateAt())));
            } catch (Exception e) {
                logger.error("转换格式错误", e);
            }
        }
        return blogs;
    }

    @Override
    public List<Blog> getBanner() {
        List<Blog> blogs = blogMapper.getBanner();
        for (Blog blog : blogs) {
            blog.setCategory(categoryMapper.selectByPrimaryKey(blog.getCategoryid()));
            try {
                blog.setCreateAt(df.format(df.parse(blog.getCreateAt())));
            } catch (Exception e) {
                logger.error("转换格式错误", e);
            }
        }
        return blogs;
    }

    @Override
    public List<Blog> getByHits() {
        List<Blog> blogs = blogMapper.getHits();
        for (Blog blog : blogs) {
            blog.setCategory(categoryMapper.selectByPrimaryKey(blog.getCategoryid()));
            try {
                blog.setCreateAt(df.format(df.parse(blog.getCreateAt())));
            } catch (Exception e) {
                logger.error("转换格式错误", e);
            }
        }
        return blogs;
    }

    @Override
    public List<Blog> getLife() {
        List<Blog> blogs = blogMapper.getLife();
        for (Blog blog : blogs) {
            blog.setCategory(categoryMapper.selectByPrimaryKey(blog.getCategoryid()));
            try {
                blog.setCreateAt(df.format(df.parse(blog.getCreateAt())));
            } catch (Exception e) {
                logger.error("转换格式错误", e);
            }
        }
        return blogs;
    }

    @Override
    public List<Blog> getAllTechBlog() {
        List<Blog> blogs = blogMapper.getAllTechBlog();
        for (Blog blog : blogs) {
            blog.setCategory(categoryMapper.selectByPrimaryKey(blog.getCategoryid()));
            try {
                blog.setCreateAt(df.format(df.parse(blog.getCreateAt())));
            } catch (Exception e) {
                logger.error("转换格式错误", e);
            }
        }
        return blogs;
    }

    @Override
    public Integer insertblog(Blog blog) {
        blog.setSummary(blog.getContent().length() > 120 ? blog.getContent().substring(0, 120) : blog.getContent());
        blog.setCreateAt(DateTime.now().toString("yyyy-MM-dd"));
        Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(blog.getContent());
        String image_url = null;
        while (matcher.find()) {
            image_url = matcher.group();
        }
        blog.setImageurl(image_url == null ? "http://ohlrxdl4p.bkt.clouddn.com/timthumb_019.jpg" : image_url);
        String array[] = blog.getTagforsplit().split(",");
        List<Tag> tags = new ArrayList<>();
        for (String string : array) {
            if (tagMapper.selectByName(string) == null) {
                Tag tag = new Tag();
                tag.settName(string);
                tagMapper.insert(tag);
            } else {
                tags.add(tagMapper.selectByName(string));
            }
        }
        blog.setTags(tags.size() == 0 ? null : tags);
        blogMapper.insert(blog);
        if (tags.size() > 0) {
            for (Tag tag : tags) {
                Relation relation = new Relation();
                relation.setBlogid(blog.getBlogid());
                relation.settId(tag.gettId());
                relationMapper.insert(relation);
            }
        }
        return blog.getBlogid();
    }

    @Override
    public Integer updateByPrimaryKeySelective(Blog blog) {
        blog.setSummary(blog.getContent().length() > 120 ? blog.getContent().substring(0, 120) : blog.getContent());
        Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(blog.getContent());
        String image_url = null;
        while (matcher.find()) {
            image_url = matcher.group();
        }
        blog.setImageurl(image_url == null ? "http://ohlrxdl4p.bkt.clouddn.com/timthumb_019.jpg" : image_url);
        String array[] = blog.getTagforsplit().split(",");
        List<Tag> tags = new ArrayList<>();
        for (String string : array) {
            if (tagMapper.selectByName(string) == null) {
                Tag tag = new Tag();
                tag.settName(string);
                tagMapper.insert(tag);    // TODO 为了下面的relation增加标签id
                tags.add(tag);
            } else {
                tags.add(tagMapper.selectByName(string));
            }
        }
        blog.setTags(tags.size() == 0 ? null : tags);
        blogMapper.updateByPrimaryKeySelective(blog);
        relationMapper.deletebyblogid(blog.getBlogid());
        if (tags.size() > 0) {
            for (Tag tag : tags) {
                Relation relation = new Relation();
                relation.setBlogid(blog.getBlogid());
                relation.settId(tag.gettId());
                relationMapper.insert(relation);
            }
        }
        return blog.getBlogid();
    }
}
