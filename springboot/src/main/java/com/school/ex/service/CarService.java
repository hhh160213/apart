package com.school.ex.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.school.ex.entity.OwnerVO;
import com.school.ex.entity.PageVO;
import com.school.ex.pojo.Car_inout;
import com.school.ex.pojo.Car_no;

import java.util.List;

public interface CarService {
    IPage getXQCarList(PageVO pageVO, String carNo, String ownerName);
    IPage getCarInList(PageVO pageVO,String carNo,String isAccess);
    int saveXQCarNo(OwnerVO ownerVO);
    String catInOut(String carNo,int type);
}
