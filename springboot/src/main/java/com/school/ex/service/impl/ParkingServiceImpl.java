package com.school.ex.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.school.ex.dao.ParkingDao;
import com.school.ex.entity.PageVO;
import com.school.ex.pojo.Parking;
import com.school.ex.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingServiceImpl implements ParkingService {
    @Autowired
    private ParkingDao parkingDao;

    @Override
    public List<Parking> getList(String id, String owner, String address) {
        LambdaQueryWrapper<Parking> queryWrapper = new LambdaQueryWrapper<>();

        if(!id.equals("")){
            queryWrapper.eq(Parking::getId,id);
        }
        if(!owner.equals("")){
            queryWrapper.like(Parking::getOwner,owner);
        }
        if(!address.equals("")){
            queryWrapper.like(Parking::getAddress,address);
        }
        List<Parking> list = parkingDao.selectList(queryWrapper);
        return list;
    }

    @Override
    public int update(Parking parking) {
        if(parking.getId()==0){
            Parking temp = new Parking();
            temp.setState("无人使用");
            parkingDao.insert(temp);
            return 1;
        }
        int result = parkingDao.updateById(parking);
        return result;
    }
}
