package com.school.ex.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.school.ex.dao.CarInDao;
import com.school.ex.dao.CarnoDao;
import com.school.ex.dao.OwnerDao;
import com.school.ex.entity.OwnerVO;
import com.school.ex.entity.PageVO;
import com.school.ex.pojo.Car_inout;
import com.school.ex.pojo.Car_no;
import com.school.ex.pojo.Pay;
import com.school.ex.service.CarService;
import com.school.ex.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarnoDao carnoDao;
    @Autowired
    private CarInDao carInDao;

    @Autowired
    private OwnerDao ownerDao;

    @Autowired
    private PayService payService;


    @Override
    public IPage getXQCarList(PageVO pageVO,String carNo, String ownerName) {
        LambdaQueryWrapper<Car_no> queryWrapper = new LambdaQueryWrapper<>();
        if(carNo==null) {
            carNo="";
        }
        if(ownerName==null) {
            ownerName="";
        }
        queryWrapper.like(Car_no::getCarNo,carNo);
        queryWrapper.like(Car_no::getOwnerName,ownerName);
        Page<Car_no> page = new Page<>(pageVO.getPage(),pageVO.getLimit());
        IPage<Car_no> list = carnoDao.selectPage(page,queryWrapper);
        return list;
    }

    @Override
    public IPage getCarInList(PageVO pageVO, String carNo,String isAccess) {
        LambdaQueryWrapper<Car_inout> queryWrapper = new LambdaQueryWrapper<>();
        if(carNo==null) {
            carNo="";
        }
        queryWrapper.like(Car_inout::getCarNo,carNo);
        if(!isAccess.isEmpty()){
            queryWrapper.eq(Car_inout::getIsAccess,isAccess);
        }
        Page<Car_inout> page = new Page<>(pageVO.getPage(),pageVO.getLimit());
        IPage<Car_inout> list = carInDao.selectPage(page,queryWrapper);
        return list;
    }

    //原理 删除原来的直接新增
    @Override
    public int saveXQCarNo(OwnerVO ownerVO) {
        if(ownerVO.getCar1().equals("null")){
            ownerVO.setCar1("");
        }
        LambdaQueryWrapper<Car_no> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Car_no::getOwnerId,ownerVO.getId());
        carnoDao.delete(queryWrapper);
        //无车 删除所有车牌信息
        if(ownerVO.getHaveCar().equals("无")){
            return 0;
        }

        Car_no car = new Car_no();
        car.setOwnerId(ownerVO.getId());
        car.setOwnerName(ownerVO.getName());
        int count = 0;
        if(!ownerVO.getCar1().isEmpty()){
            car.setCarNo(ownerVO.getCar1());
            count +=carnoDao.insert(car);
        }
        if(!ownerVO.getCar2().isEmpty()){
            car.setCarNo(ownerVO.getCar2());
            count +=carnoDao.insert(car);
        }
        return count;


    }

    @Override
    public String catInOut(String carNo, int type) {
        LambdaQueryWrapper<Car_inout> queryWrapper = new LambdaQueryWrapper<>();
        //进入
        if(type==1){
            queryWrapper.eq(Car_inout::getCarNo,carNo);
            queryWrapper.isNull(Car_inout::getOutTime);
            List<Car_inout> car_inout = carInDao.selectList(queryWrapper);
            if(car_inout.size()!=0){
                return "有车辆有未离开小区信息,先登记结算离开";
            }

            Car_inout car = new Car_inout();
            car.setInTime(new Date());
            car.setCarNo(carNo);
            LambdaQueryWrapper<Car_no> noLambdaQueryWrapper = new LambdaQueryWrapper<>();
            noLambdaQueryWrapper.eq(Car_no::getCarNo,carNo);
            Car_no order = carnoDao.selectOne(noLambdaQueryWrapper);
            if(order!=null){
                car.setIsAccess(1);
            }else{
                car.setIsAccess(0);
            }
            carInDao.insert(car);
            return null;

        }
        //离开
        else{
            queryWrapper.eq(Car_inout::getCarNo,carNo);
            queryWrapper.isNull(Car_inout::getOutTime);
            Car_inout car_inout = carInDao.selectOne(queryWrapper);
            if(car_inout==null){
                return "无车辆进入小区记录";
            }
            car_inout.setOutTime(new Date());
            long time = (car_inout.getOutTime().getTime()-car_inout.getInTime().getTime())/1000/60/60;
            carInDao.updateById(car_inout);
            if(car_inout.getIsAccess()==0){
                SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Pay pay = new Pay();
                pay.setPayName("外来车辆停车费");
                pay.setStartTime(dfs.format(car_inout.getInTime()));
                pay.setEndTime(dfs.format(car_inout.getOutTime()));
                pay.setPayMoney(Long.toString(time*10+10));
                pay.setStatus("未支付");
                pay.setApartment(carNo);
                payService.insert(pay);
            }
        }
        return null;
    }
}
