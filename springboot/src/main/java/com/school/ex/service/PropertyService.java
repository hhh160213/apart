package com.school.ex.service;

import com.school.ex.entity.ApiResult;
import com.school.ex.pojo.Property;

import java.util.List;

public interface PropertyService {
    List<Property> getList(String name);
    ApiResult changePwd(Property property,String newPwd);
    int del(int id);

    ApiResult initPwd(String adminAcc, String pwd, int id);
}
