package com.apponex.eLibaryManagment.dataAccess.cargo;

import com.apponex.eLibaryManagment.entity.cargo.Tracker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackerRepository extends JpaRepository<Tracker,Integer> {
}
