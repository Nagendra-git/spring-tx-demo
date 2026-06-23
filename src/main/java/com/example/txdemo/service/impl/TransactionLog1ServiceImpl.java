package com.example.txdemo.service.impl;

import com.example.txdemo.dto.TransactionLogDto;
import com.example.txdemo.entity.TransactionLog1;
import com.example.txdemo.repository.TransactionLog1Repository;
import com.example.txdemo.service.TransactionLog1Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service implementation for managing {@code TransactionLog1} persistence operations.
 *
 * <p>Handles the creation and storage of transaction log entries
 * into the {@code transaction_log1} table via {@link TransactionLog1Repository}.</p>
 */
@Service
@RequiredArgsConstructor
public class TransactionLog1ServiceImpl implements TransactionLog1Service {

    /**
     * Repository for performing CRUD operations on {@code TransactionLog1} entities.
     */
    private final TransactionLog1Repository transactionLog1Repository;

    /**
     * Persists a new log entry to the {@code transaction_log1} table.
     *
     * <p>Extracts {@code message1} from the provided DTO, maps it to a
     * {@link TransactionLog1} entity with the current timestamp, and saves it
     * to the database.</p>
     *
     * @param transactionLogDto the DTO containing the log message to be persisted;
     *                          must not be {@code null} and {@code message1} must not be {@code null}
     */
    @Override
    public void addTransactionLog(TransactionLogDto transactionLogDto) {
        TransactionLog1 transactionLog1 = new TransactionLog1();
        transactionLog1.setMessage(transactionLogDto.getMessage1());
        transactionLog1.setCreatedAt(LocalDateTime.now());
        transactionLog1Repository.save(transactionLog1);
    }
}
