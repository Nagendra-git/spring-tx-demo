package com.example.txdemo.service.impl;

import com.example.txdemo.dto.TransactionLogDto;
import com.example.txdemo.entity.TransactionLog2;
import com.example.txdemo.repository.TransactionLog2Repository;
import com.example.txdemo.service.TransactionLog2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service implementation for managing {@code TransactionLog2} persistence operations.
 *
 * <p>Handles the creation and storage of transaction log entries
 * into the {@code transaction_log2} table via {@link TransactionLog2Repository}.</p>
 */
@Service
@RequiredArgsConstructor
public class TransactionLog2ServiceImpl implements TransactionLog2Service {

    /**
     * Repository for performing CRUD operations on {@code TransactionLog2} entities.
     */
    private final TransactionLog2Repository transactionLog2Repository;

    /**
     * Persists a new log entry to the {@code transaction_log2} table.
     *
     * <p>Extracts {@code message2} from the provided DTO, maps it to a
     * {@link TransactionLog2} entity, and saves it to the database.</p>
     *
     * <p><strong>Note:</strong> {@code LocalDateTime.parse("Genara")} is not a valid
     * date-time string and will throw a {@link java.time.format.DateTimeParseException}
     * at runtime, causing the transaction to roll back. This should be replaced
     * with {@code LocalDateTime.now()} or a valid date-time value.</p>
     *
     * @param transactionLogDto the DTO containing the log message to be persisted;
     *                          must not be {@code null} and {@code message2} must not be {@code null}
     * @throws java.time.format.DateTimeParseException if the invalid date-time
     *         string {@code "Genara"} is not resolved before invocation
     */
    @Override
    public void addTransactionLog(TransactionLogDto transactionLogDto) {
        TransactionLog2 transactionLog2 = new TransactionLog2();
        transactionLog2.setCreatedAt(LocalDateTime.parse("Genara"));
        transactionLog2.setMessage(transactionLogDto.getMessage2());
        transactionLog2Repository.save(transactionLog2);
    }
}
