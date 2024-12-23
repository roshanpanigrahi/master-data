package com.bustravelagent.masterdata.repository;

import com.bustravelagent.masterdata.model.BusRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface BusRouteRepository extends JpaRepository<BusRoute, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE BusRoute b SET b.source = :source, b.destination = :destination, b.price = :price WHERE b.busNo = :busNo")
    int updateBusRouteDetails(Integer busNo, String source, String destination, Integer price);


    BusRoute getBusRouteBySourceAndDestination(String source, String destination);
    BusRoute getBusRouteByBusNo(Integer busNo);



}

