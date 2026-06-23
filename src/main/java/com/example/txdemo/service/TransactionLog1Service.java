package com.example.txdemo.service;

import com.example.txdemo.dto.TransactionLogDto;
import org.springframework.stereotype.Service;

@Service
public interface TransactionLog1Service {

    void addTransactionLog(TransactionLogDto transactionLogDto);
}
