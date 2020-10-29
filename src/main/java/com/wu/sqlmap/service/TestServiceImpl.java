package com.wu.sqlmap.service;

import com.wu.sqlmap.mapper.UserMapper;
import com.wu.sqlmap.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestServiceI {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserModel> getInject(String name) {
        return userMapper.getInject(name);
    }

    @Override
    public List<UserModel> getSafely(String name) {
        return userMapper.getSafely(name);
    }
}
