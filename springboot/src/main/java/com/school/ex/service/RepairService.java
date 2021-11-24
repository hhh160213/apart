package com.school.ex.service;

import com.school.ex.entity.PageVO;
import com.school.ex.pojo.Owner;
import com.school.ex.pojo.Property;
import com.school.ex.pojo.Repairer;
import com.school.ex.pojo.Words;

import java.util.List;

public interface RepairService {
//    public Property loginProperty(String account, String password);
//    public Owner loginOwner(String tel,String pwd);
//    public int loginOut();
//    public List<Owner> getOwnerList(PageVO pageVO,String userName,String userMobile);
    public int insertOwner(Repairer repairer);
    List<Repairer> getList();
    List<Repairer> getBYnameList(String name);
    int repairhandle(int id,String complete);
    int del(int id);

}
