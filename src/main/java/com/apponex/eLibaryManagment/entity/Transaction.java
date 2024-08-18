package com.apponex.eLibaryManagment.entity;

import com.apponex.eLibaryManagment.core.common.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
public class Transaction extends BaseEntity {
    private boolean returned;
    private boolean approved;

}
