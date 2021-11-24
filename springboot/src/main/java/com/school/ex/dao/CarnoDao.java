package com.school.ex.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.school.ex.pojo.Car_no;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CarnoDao extends BaseMapper<Car_no> {
}
