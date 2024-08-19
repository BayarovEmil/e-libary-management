package com.apponex.eLibaryManagment.entity.cargo;

import com.apponex.eLibaryManagment.core.common.BaseEntity;
import com.apponex.eLibaryManagment.entity.operation.Transaction;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Tracker extends BaseEntity {
    private String trackingNumber;
    private double shippingCost;
    private TrackingStatus trackingStatus;
    private LocalDate orderingDate;
    private LocalDate deliveryDate;

    @ManyToOne
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;

    @OneToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;
}
