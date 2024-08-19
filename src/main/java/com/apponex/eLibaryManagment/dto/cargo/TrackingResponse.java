package com.apponex.eLibaryManagment.dto.cargo;

import com.apponex.eLibaryManagment.entity.cargo.TrackingStatus;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TrackingResponse(
        String trackingNumber,
        double shippingCost,
        TrackingStatus trackingStatus,
        LocalDate orderDate,
        LocalDate deliveryDate
) {
}
