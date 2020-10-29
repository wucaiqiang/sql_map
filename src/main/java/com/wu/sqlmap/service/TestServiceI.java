package com.wu.sqlmap.service;

import com.wu.sqlmap.model.UserModel;

import java.util.List;

public interface TestServiceI {
    List<UserModel> getInject(String name);

    List<UserModel> getSafely(String name);
}
