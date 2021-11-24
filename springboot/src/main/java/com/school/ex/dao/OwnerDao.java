package com.school.ex.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.school.ex.entity.OwnerVO;
import com.school.ex.pojo.Owner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OwnerDao extends BaseMapper<Owner> {
    Owner getOwnerByPwd(String account,String password);
    Owner getOwnerByTel(String tel);
    Owner getOwnerBy(String apartment,String doorId);
    List<String> getApartmentListByName(String name);
    List<String> getDoorIdByApartment(String doorId);
    IPage<OwnerVO> getOwnerList(Page<OwnerVO> page, @Param("userName") String userName, @Param("tel") String tel);

}
