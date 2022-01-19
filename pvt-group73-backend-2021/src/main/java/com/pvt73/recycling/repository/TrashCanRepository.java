package com.pvt73.recycling.repository;

import com.pvt73.recycling.model.dao.LatLng;
import com.pvt73.recycling.model.dao.TrashCan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrashCanRepository extends CrudRepository<TrashCan, LatLng> {

    List<TrashCan> findAll();
}
