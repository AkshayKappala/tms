package com.kappala.tms.persistence.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Transaction {
    @Id
    @GeneratedValue
    private int id;

    private BigDecimal amount;
    
    @Enumerated(EnumType.STRING)
    private TransactionState status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
