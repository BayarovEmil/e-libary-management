package com.apponex.eLibaryManagment.dto.cargo;

import lombok.Builder;

@Builder
public record CargoResponse(
        String companyName,
        String ownerName,
        String address
) {
}
