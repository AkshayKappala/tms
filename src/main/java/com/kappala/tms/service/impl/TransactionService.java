package com.kappala.tms.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.kappala.tms.persistence.model.Transaction;
import com.kappala.tms.persistence.repository.ITransactionRepository;
import com.kappala.tms.service.ITransactionService;

public class TransactionService implements ITransactionService {

    @Autowired
    private ITransactionRepository transactionRepository;

    @Override
    public Transaction createTransaction(BigDecimal amount) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createTransaction'");
    }

    @Override
    public List<Transaction> getAllTransactions() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllTransactions'");
    }

    @Override
    public void settleOldTransactions() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'settleOldTransactions'");
    }

}
