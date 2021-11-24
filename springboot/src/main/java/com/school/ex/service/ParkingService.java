package com.school.ex.service;

import com.school.ex.pojo.Parking;

import java.util.List;

public interface ParkingService {
    List<Parking> getList(String id,String owner,String address);
    int update(Parking parking);
}
