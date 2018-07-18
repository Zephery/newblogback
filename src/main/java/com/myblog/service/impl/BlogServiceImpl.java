package com.myblog.service.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.myblog.dao.BlogMapper;
import com.myblog.dao.CategoryMapper;
import com.myblog.dao.RelationMapper;
import com.myblog.dao.TagMapper;
import com.myblog.model.Blog;
import com.myblog.model.Category;
import com.myblog.model.Relation;
import com.myblog.model.Tag;
import com.myblog.service.IBlogService;
import com.myblog.util.HttpHelper;
import com.myblog.util.Tools;
import org.apache.commons.lang.StringEscapeUtils;
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
    private static final Logger logger = LoggerFactory.getLogger(BlogServiceImpl.class);
    @Resource
    private BlogMapper blogMapper;
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private RelationMapper relationMapper;
    @Resource
    private TagMapper tagMapper;
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    //    正则表达式
    private static final String IMGSRC_REG = "(http|https)://.*\\.(jpg|png|gif)";
    //    当博客中没有图片的时候
    private static final String[] PICTURES = {
            "http://image.wenzhihuai.com/images/20171104105733.png",
            "http://image.wenzhihuai.com/images/20171104105747.png",
            "http://image.wenzhihuai.com/images/20171104105808.png",
            "http://image.wenzhihuai.com/images/20171104110110.png",
            "http://image.wenzhihuai.com/images/20171104110123.png",
            "http://image.wenzhihuai.com/contentnopicture.jpg",
            "http://image.wenzhihuai.com/images/20171104110534.png"
    };

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

    /**
     * 访问七牛云获取图片的大小
     *
     * @param imageurl
     * @return
     */
    private static String getImageURl(String imageurl) {
        //        http://image.wenzhihuai.com/images/20171018051437.png?imageInfo
//        {"size":14736,"format":"png","width":667,"height":387,"colorModel":"rgba"}
        String content;
        if (imageurl == null) {
            return PICTURES[Tools.rand(0, PICTURES.length)];
        } else {
            if (imageurl.contains("upyuncdn")) {    //又拍云
                content = HttpHelper.getInstance().get(imageurl + "!imageInfo");
            } else {    //七牛云
                content = HttpHelper.getInstance().get(imageurl + "?imageInfo");
            }
            JsonParser parser = new JsonParser();
            JsonObject object = parser.parse(content).getAsJsonObject();
            Integer height = object.get("height").getAsInt();
            if (height > 350) {
                return imageurl;
            } else {
                return PICTURES[Tools.rand(0, PICTURES.length)];
            }
        }
    }

    @Override
    public synchronized Integer insertblog(Blog blog) {
        blog.setSummary(blog.getContent().length() > 120 ? blog.getContent().substring(0, 120) : blog.getContent());
        blog.setCreateAt(DateTime.now().toString("yyyy-MM-dd"));
        Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(blog.getContent());
        String image_url = null;
        while (matcher.find()) {
            image_url = matcher.group();
        }
//        http://image.wenzhihuai.com/images/20171018051437.png?imageInfo
//        {"size":14736,"format":"png","width":667,"height":387,"colorModel":"rgba"}

        blog.setImageurl(getImageURl(image_url));
        String array[] = blog.getTagforsplit().split(",");
        List<Tag> tags = new ArrayList<>();
        for (String string : array) {
            if (tagMapper.selectByName(string) == null) {
                Tag tag = new Tag();
                tag.settName(string);
                tagMapper.insertSelective(tag);
                Tag newtag = tagMapper.selectByName(string);
                tags.add(newtag);
//                tag.settId(tId);//TODO 返回主键长不起作用
//                tags.add(tag);
            } else {
                tags.add(tagMapper.selectByName(string));
            }
        }
        blog.setTags(tags.size() == 0 ? null : tags);
        blog.setHits(0);
        blog.setContent(StringEscapeUtils.escapeSql(blog.getContent()));
        blogMapper.insert(blog);
        Blog newblog = blogMapper.selectByTitle(blog.getTitle());
        if (tags.size() > 0) {
            for (Tag tag : tags) {
                Relation relation = new Relation();
                relation.setBlogid(newblog.getBlogid());
                relation.settId(tag.gettId());
                relationMapper.insert(relation);
            }
        }

        return blogMapper.selectMaxId();
    }

    @Override
    public Integer updateByPrimaryKeySelective(Blog blog) {
        blog.setSummary(blog.getContent().length() > 120 ? blog.getContent().substring(0, 120) : blog.getContent());
        Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(blog.getContent());
        String image_url = null;
        while (matcher.find()) {
            image_url = matcher.group();
        }
        blog.setImageurl(getImageURl(image_url));
        String array[] = blog.getTagforsplit().split(",");
        List<Tag> tags = new ArrayList<>();
        for (String string : array) {
            if (tagMapper.selectByName(string) == null) {
                Tag tag = new Tag();
                tag.settName(string);
                tagMapper.insert(tag);    // TODO 为了下面的relation增加标签id
                Tag newtag = tagMapper.selectByName(string);
                tags.add(newtag);
            } else {
                tags.add(tagMapper.selectByName(string));
            }
        }
        blog.setTags(tags.size() == 0 ? null : tags);
        blog.setContent(StringEscapeUtils.escapeSql(blog.getContent()));
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

    @Override
    public void delete(Integer blogid) {
        blogMapper.deleteByPrimaryKey(blogid);
    }
}
