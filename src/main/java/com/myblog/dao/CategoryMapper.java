package com.myblog.dao;

import com.myblog.model.Category;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper {
    int deleteByPrimaryKey(Integer cId);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer cId);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    List<Category> getAllCategory();

    public void saveorUpdate(@Param("cName") String cName, @Param("cId") Integer cId);
}