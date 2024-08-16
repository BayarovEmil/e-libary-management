package com.apponex.eLibaryManagment.core.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ActivationCode {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String code;

    private LocalDateTime createdAt;
    private LocalDateTime validatedAt;
    private LocalDateTime expiresAt;

    @ManyToOne
    @JoinColumn(name = "userId",nullable = false)
    private User user;
}
