package com.sydml.mybaits.dao;

import com.github.pagehelper.Page;
import com.sydml.mybaits.base.ResultMap;
import com.sydml.mybaits.dto.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {
    List<ResultMap> queryAll();

    @Select("SELECT * FROM USER")
    List<User> queryAllPageDetail();

    @Select("SELECT * FROM USER")
    Page<User> queryAllPage();
}