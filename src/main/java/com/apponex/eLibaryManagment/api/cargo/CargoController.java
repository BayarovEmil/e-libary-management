package com.apponex.eLibaryManagment.api.cargo;

import com.apponex.eLibaryManagment.business.cargo.CargoService;
import com.apponex.eLibaryManagment.core.common.PageResponse;
import com.apponex.eLibaryManagment.dto.cargo.CargoRequest;
import com.apponex.eLibaryManagment.dto.cargo.CargoResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cargo")
@Tag(name = "Cargo Controller")
@RequiredArgsConstructor
public class CargoController {
    private final CargoService cargoService;

    @PostMapping("/create")
    public ResponseEntity<CargoResponse> addCargoCompany(
            Authentication connectedUser,
            @RequestBody CargoRequest cargoRequest
    ) {
        return ResponseEntity.ok(cargoService.addCargoCompany(connectedUser, cargoRequest));
    }

    @PatchMapping("/update/{company-id}")
    public ResponseEntity<CargoResponse> updateCargoCompany(
            Authentication connectedUser,
            @RequestBody CargoRequest cargoRequest,
            @PathVariable("company-id") Integer companyId
    ) {
        return ResponseEntity.ok(cargoService.updateCargoCompany(connectedUser, companyId, cargoRequest));
    }

    @GetMapping("/read/{company-id}")
    public ResponseEntity<CargoResponse> readCargoCompany(
            @PathVariable("company-id") Integer companyId
    ) {
        return ResponseEntity.ok(cargoService.readCargoCompany(companyId));
    }

    @GetMapping("/read")
    public ResponseEntity<PageResponse<CargoResponse>> readAllCargoCompany(
            @RequestParam(name = "page",required = false,defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(cargoService.readAllCargoCompany(page,size));
    }
}
