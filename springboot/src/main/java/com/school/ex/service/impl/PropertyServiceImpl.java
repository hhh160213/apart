package com.school.ex.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.school.ex.dao.PropertyDao;
import com.school.ex.entity.ApiResult;
import com.school.ex.pojo.Property;
import com.school.ex.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    private PropertyDao propertyDao;

    @Override
    public List<Property> getList(String name) {
        LambdaQueryWrapper<Property> queryWrapper = new LambdaQueryWrapper<>();
        if(!name.equals("")){
            queryWrapper.like(Property::getName,name);
        }
        return propertyDao.selectList(queryWrapper);
    }

    @Override
    public ApiResult changePwd(Property property,String newPwd) {
        LambdaQueryWrapper<Property> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Property::getAccount,property.getAccount());
        queryWrapper.eq(Property::getPassword,property.getPassword());
        Property admin = propertyDao.selectOne(queryWrapper);
        if(admin==null){
            return new ApiResult(0,"旧密码错误",500);
        }
        property.setPassword(newPwd);
        int result = propertyDao.updateById(property);
        return new ApiResult(result);
    }

    @Override
    public int del(int id) {
        if(propertyDao.selectById(id).getPermission()=="普通员工"){
            return 0;
        }
        int result = propertyDao.deleteById(id);
        return result   ;
    }

    @Override
    public ApiResult initPwd(String adminAcc, String pwd, int id) {
        LambdaQueryWrapper<Property> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Property::getAccount,adminAcc);
        queryWrapper.eq(Property::getPassword,pwd);
        Property admin = propertyDao.selectOne(queryWrapper);
        if(admin==null){
            return new ApiResult(0,"经理密码错误",500);
        }
        Property init = new Property();
        init.setId(id);
        init.setPassword("123");
        propertyDao.updateById(init);
        return new ApiResult(1);
    }
}
