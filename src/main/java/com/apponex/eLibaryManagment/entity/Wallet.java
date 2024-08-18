package com.apponex.eLibaryManagment.entity;

import com.apponex.eLibaryManagment.core.common.BaseEntity;
import com.apponex.eLibaryManagment.core.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Wallet extends BaseEntity {
    private double balance;
    private boolean active;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "wallet",cascade = CascadeType.ALL)
    private List<WalletOperation> walletOperations;

}
