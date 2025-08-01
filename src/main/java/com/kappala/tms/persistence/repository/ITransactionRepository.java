package com.kappala.tms.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kappala.tms.persistence.model.Transaction;

public interface ITransactionRepository extends JpaRepository<Transaction, Integer>{
    // To Do
}
