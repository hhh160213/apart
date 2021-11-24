package com.school.ex.service;

import com.school.ex.entity.OwnerVO;
import com.school.ex.entity.PageVO;
import com.school.ex.pojo.Owner;
import com.school.ex.pojo.Property;
import com.school.ex.pojo.Words;

import java.util.List;

public interface IndexService {
    public Property loginProperty(String account, String password);
    public Owner loginOwner(String tel,String pwd);
    public int loginOut();
    public List<OwnerVO> getOwnerList(PageVO pageVO, String userName, String userMobile);
    public List<Owner> getrepaieOwnerList(String userName);
    public int insertOwner(Owner owner);
    public int updateOwner(OwnerVO owner);
    List<Owner> getallList();
    int updateOwenerPwd(int id,String old,String newpwd);

}
