package com.apponex.eLibaryManagment.business.cargo;

import com.apponex.eLibaryManagment.core.common.PageResponse;
import com.apponex.eLibaryManagment.core.entity.User;
import com.apponex.eLibaryManagment.dataAccess.cargo.CargoRepository;
import com.apponex.eLibaryManagment.dataAccess.cargo.TrackerRepository;
import com.apponex.eLibaryManagment.dataAccess.operation.TransactionRepository;
import com.apponex.eLibaryManagment.dto.cargo.CargoRequest;
import com.apponex.eLibaryManagment.dto.cargo.CargoResponse;
import com.apponex.eLibaryManagment.dto.cargo.TrackingResponse;
import com.apponex.eLibaryManagment.entity.book.Book;
import com.apponex.eLibaryManagment.entity.cargo.Cargo;
import com.apponex.eLibaryManagment.entity.cargo.Tracker;
import com.apponex.eLibaryManagment.entity.cargo.TrackingStatus;
import com.apponex.eLibaryManagment.mapper.cargo.CargoMapper;
import com.apponex.eLibaryManagment.mapper.cargo.TrackingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CargoService implements ICargoService{
    private final CargoRepository cargoRepository;
    private final CargoMapper cargoMapper;
    private final TrackerRepository trackerRepository;
    private final TrackingMapper trackingMapper;
    public CargoResponse addCargoCompany(Authentication connectedUser, CargoRequest cargoRequest) {
        User user = (User) connectedUser.getPrincipal();
        var cargo = cargoMapper.toCargo(cargoRequest);
        cargo.setOwner(user.getFirstname());
        cargoRepository.save(cargo);
        return cargoMapper.toCargoResponse(cargo);
    }

    public CargoResponse updateCargoCompany(Authentication connectedUser, Integer companyId, CargoRequest cargoRequest) {
        var cargo = cargoRepository.findById(companyId)
                        .orElseThrow(()->new IllegalStateException("Cannot find company"));
        cargoRepository.save(cargo);
        return cargoMapper.toCargoResponse(cargo);
    }

    public CargoResponse readCargoCompany(Integer companyId) {
        var cargo = cargoRepository.findById(companyId)
                .orElseThrow(()->new IllegalStateException("Cannot find company"));
        return cargoMapper.toCargoResponse(cargo);
    }

    public PageResponse<CargoResponse> readAllCargoCompany(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Cargo> cargos = cargoRepository.findAll(pageable);
        List<CargoResponse> cargoResponses = cargos.stream()
                .map(cargoMapper::toCargoResponse)
                .toList();
        return new PageResponse<>(
                cargoResponses,
                cargos.getNumber(),
                cargos.getSize(),
                cargos.getTotalPages(),
                cargos.getTotalElements(),
                cargos.isFirst(),
                cargos.isLast()
        );
    }

    @Override
    public TrackingResponse sendCargoAndSave(String trackingNumber, double shippingCost) {

        Tracker tracker = Tracker.builder()
                .trackingStatus(TrackingStatus.SHIPPED)
                .orderingDate(LocalDate.now())
                .deliveryDate(LocalDate.now().plusDays(5))
                .trackingNumber(trackingNumber)
                .shippingCost(shippingCost)
                .build();
        tracker.setCargo(Cargo.builder().id(1).build());
        return trackingMapper.toTrackingResponse(trackerRepository.save(tracker));
    }

    @Override
    public double calculateShippingCost(Book book) {
        return book.getPrice() * 0.1;
    }

    @Override
    public String calculateTrackingNumber() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String datePart = LocalDateTime.now().format(formatter);

        // Generate a random 4-digit number
        String randomPart = String.valueOf(new Random().nextInt(9000) + 1000);

        // Combine parts to form the tracking number
        return "TRACK-" + datePart + "-" + randomPart;
    }
}
