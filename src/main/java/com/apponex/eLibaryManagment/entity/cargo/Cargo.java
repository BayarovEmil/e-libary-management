package com.apponex.eLibaryManagment.entity.cargo;

import com.apponex.eLibaryManagment.core.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
public class Cargo extends BaseEntity {
    private String companyName;
    private String owner;
    private String location;

    @OneToMany(mappedBy = "cargo")
    private List<Tracker> trackers;
}
