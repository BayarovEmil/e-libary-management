package com.apponex.eLibaryManagment.core.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Token {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType = TokenType.BEARER;
    private boolean revoked;
    private boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId",nullable = false)
    private User user;
}
