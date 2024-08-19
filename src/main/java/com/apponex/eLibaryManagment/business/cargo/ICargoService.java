package com.apponex.eLibaryManagment.business.cargo;

import com.apponex.eLibaryManagment.core.entity.User;
import com.apponex.eLibaryManagment.dto.cargo.TrackingResponse;
import com.apponex.eLibaryManagment.entity.book.Book;

public interface ICargoService {
    default TrackingResponse takeOrder(User user, Book book) {
        var trackingNumber = calculateTrackingNumber();
        var shippingCost = calculateShippingCost(book);
        return sendCargoAndSave(trackingNumber, shippingCost);
    }

    TrackingResponse sendCargoAndSave(String trackingNumber, double shippingCost);

    double calculateShippingCost(Book book);

    String calculateTrackingNumber();
}
