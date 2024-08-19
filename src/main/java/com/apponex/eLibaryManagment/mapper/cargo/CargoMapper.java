package com.apponex.eLibaryManagment.mapper.cargo;

import com.apponex.eLibaryManagment.dto.cargo.CargoRequest;
import com.apponex.eLibaryManagment.dto.cargo.CargoResponse;
import com.apponex.eLibaryManagment.entity.cargo.Cargo;
import org.springframework.stereotype.Service;

@Service
public class CargoMapper {
    public CargoResponse toCargoResponse(Cargo cargo) {
        return CargoResponse.builder()
                .companyName(cargo.getCompanyName())
                .address(cargo.getLocation())
                .ownerName(cargo.getOwner())
                .build();
    }


    public Cargo toCargo(CargoRequest request) {
        return Cargo.builder()
                .companyName(request.companyName())
                .location(request.address())
                .build();
    }
}
