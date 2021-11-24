package com.school.ex.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.school.ex.pojo.Parking;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ParkingDao extends BaseMapper<Parking> {
//    public List<Parking> getParkingList();
}
