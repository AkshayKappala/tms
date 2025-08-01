package com.kappala.tms.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kappala.tms.service.ITransactionService;

@Component
public class SettlementScheduler {
    @Autowired
    private ITransactionService transactionService;

    @Scheduled(fixedRate = 60000) // run every 60 seconds
    public void run() {
        transactionService.settleOldTransactions();
    }
}
