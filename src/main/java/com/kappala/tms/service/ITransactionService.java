package com.kappala.tms.service;

import java.math.BigDecimal;
import java.util.List;

import com.kappala.tms.persistence.model.Transaction;

public interface ITransactionService {
    Transaction createTransaction(BigDecimal amount);
    List<Transaction> getAllTransactions();
    void settleOldTransactions();    
}
