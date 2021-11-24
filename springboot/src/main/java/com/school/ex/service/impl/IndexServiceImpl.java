package com.school.ex.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.school.ex.dao.CarnoDao;
import com.school.ex.dao.OwnerDao;
import com.school.ex.dao.PropertyDao;
import com.school.ex.entity.OwnerVO;
import com.school.ex.entity.PageVO;
import com.school.ex.pojo.*;
import com.school.ex.service.CarService;
import com.school.ex.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private OwnerDao ownerDao;

    @Autowired
    private CarService carService;

    @Autowired
    private CarnoDao carnoDao;

    @Autowired
    private PropertyDao propertyDao;
    @Override
    public Property loginProperty(String account, String password) {

        LambdaQueryWrapper<Property> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Property::getAccount,account);
        queryWrapper.eq(Property::getPassword,password);
        return propertyDao.selectOne(queryWrapper);
    }
    public Owner loginOwner(String tel,String pwd){
        LambdaQueryWrapper<Owner> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Owner::getTel,tel);
        queryWrapper.eq(Owner::getPwd,pwd);
        return ownerDao.selectOne(queryWrapper);
    }
    @Override
    public int loginOut() {
        return 0;
    }

    @Override
    public int insertOwner(Owner owner) {
        return ownerDao.insert(owner);
    }

    @Override
    public int updateOwner(OwnerVO owner) {
        if(owner.getId()!=0){
            int count = ownerDao.updateById(owner);
            carService.saveXQCarNo(owner);
            return ownerDao.updateById(owner);
        }else{
            Owner check = ownerDao.getOwnerByTel(owner.getTel());
            if(check!=null){
                return 0;
            }else{
                int count = 0;
                count += ownerDao.insert(owner);
                count += carService.saveXQCarNo(owner);
                return count;
            }
        }
    }

    @Override
    public List<Owner> getallList() {
        List<Owner> allList= ownerDao.selectList(null);

        return allList;
    }

    @Override
    public List<OwnerVO> getOwnerList(PageVO pageVO, String userName, String userMobile) {
        Page<OwnerVO> page = new Page<>(pageVO.getPage(),pageVO.getLimit());
        IPage<OwnerVO> iPage = ownerDao.getOwnerList(page,userName,userMobile);
        return iPage.getRecords();
    }

    @Override
    public List<Owner> getrepaieOwnerList(String userName) {
        QueryWrapper<Owner> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",userName);
        List<Owner>   Namelist=ownerDao.selectList(queryWrapper);
        return Namelist;

    }

    @Override
    public int updateOwenerPwd(int id, String old, String newpwd) {
        LambdaQueryWrapper<Owner> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Owner::getId,id);
        queryWrapper.eq(Owner::getPwd,old);
        Owner owner = ownerDao.selectOne(queryWrapper);
        if(owner==null){
            return 0;
        }
        owner.setPwd(newpwd);
        return ownerDao.updateById(owner);
    }
}
