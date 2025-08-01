package com.kappala.tms.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kappala.tms.persistence.model.Transaction;
import com.kappala.tms.persistence.model.TransactionState;
import com.kappala.tms.persistence.repository.ITransactionRepository;
import com.kappala.tms.service.ITransactionService;

@Service
public class TransactionService implements ITransactionService {

    @Autowired
    private ITransactionRepository transactionRepository;

    @Override
    @Transactional
    public Transaction createTransaction(BigDecimal amount) {
        Transaction tx = new Transaction();
        tx.setAmount(amount);
        tx.setStatus(TransactionState.PENDING);
        tx.setCreatedAt(LocalDateTime.now());
        tx.setUpdatedAt(LocalDateTime.now());
        return transactionRepository.save(tx);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    @Transactional
    public void settleOldTransactions() {
        LocalDateTime cutoff = LocalDateTime.now().minusHours(24);
        List<Transaction> old = transactionRepository.findByStatusAndCreatedAtBefore(TransactionState.PENDING, cutoff);
        for (Transaction tx : old) {
            tx.setStatus(TransactionState.COMPLETED);
            tx.setUpdatedAt(LocalDateTime.now());
        }
        transactionRepository.saveAll(old);
    }
}
