package com.wu.sqlmap.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wu.sqlmap.model.UserModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<UserModel> {

    public List<UserModel> getInject(@Param("name") String name);

    public List<UserModel> getSafely(@Param("name") String name);
}
