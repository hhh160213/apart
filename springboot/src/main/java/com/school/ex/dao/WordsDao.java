package com.school.ex.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.school.ex.pojo.Parking;
import com.school.ex.pojo.Words;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface WordsDao extends BaseMapper<Words> {
//    public List<Parking> getParkingList();
}
