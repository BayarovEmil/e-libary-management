package com.apponex.eLibaryManagment.dataAccess.cargo;

import com.apponex.eLibaryManagment.entity.cargo.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoRepository extends JpaRepository<Cargo,Integer> {
}
