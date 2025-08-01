package com.kappala.tms.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kappala.tms.persistence.model.Transaction;
import com.kappala.tms.persistence.model.TransactionState;

public interface ITransactionRepository extends JpaRepository<Transaction, Integer>{
    List<Transaction> findByStatusAndCreatedAtBefore(TransactionState status, LocalDateTime createdAt);
}
