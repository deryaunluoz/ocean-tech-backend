package com.oceantech.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "credit_cards")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardNo;

    private Integer expireMonth;

    private Integer expireYear;

    private String nameOnCard;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}