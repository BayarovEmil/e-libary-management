package com.apponex.eLibaryManagment.mapper.cargo;

import com.apponex.eLibaryManagment.dto.cargo.TrackingResponse;
import com.apponex.eLibaryManagment.entity.cargo.Tracker;
import org.springframework.stereotype.Service;

@Service
public class TrackingMapper {

    public TrackingResponse toTrackingResponse(Tracker tracker) {
        return TrackingResponse.builder()
                .trackingNumber(tracker.getTrackingNumber())
                .shippingCost(tracker.getShippingCost())
                .trackingStatus(tracker.getTrackingStatus())
                .orderDate(tracker.getOrderingDate())
                .deliveryDate(tracker.getDeliveryDate())
                .build();
    }
}
