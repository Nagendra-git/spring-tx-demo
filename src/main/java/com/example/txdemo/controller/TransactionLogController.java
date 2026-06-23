package com.example.txdemo.controller;

import com.example.txdemo.dto.TransactionLogDto;
import com.example.txdemo.facade.TransactionLogFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller exposing endpoints for managing transaction logs.
 *
 * <p>Acts as the entry point for transaction-log-related HTTP requests,
 * delegating all business logic to {@link TransactionLogFacade}.
 *
 * <p>All endpoints are registered under the application's root context path.
 */
@Slf4j
@RestController
@RequestMapping("/api/transaction-logs")
@RequiredArgsConstructor
public class TransactionLogController {

    private final TransactionLogFacade transactionLogFacade;

    /**
     * Adds a transaction log using REQUIRED propagation (default).
     * Joins existing transaction or creates a new one.
     */
    @PostMapping("/required")
    public ResponseEntity<String> addWithRequired(@RequestBody TransactionLogDto dto) {
        log.info("REST request to add transaction log with REQUIRED propagation");
        String result = transactionLogFacade.addWithRequired(dto);
        log.info("REQUIRED propagation completed successfully");
        return ResponseEntity.ok(result);
    }

    /**
     * Adds a transaction log using REQUIRES_NEW propagation.
     * Always creates a new independent transaction.
     */
    @PostMapping("/requires-new")
    public ResponseEntity<String> addWithRequiresNew(@RequestBody TransactionLogDto dto) {
        log.info("REST request to add transaction log with REQUIRES_NEW propagation");
        String result = transactionLogFacade.addWithRequiresNew(dto);
        log.info("REQUIRES_NEW propagation completed successfully");
        return ResponseEntity.ok(result);
    }

    /**
     * Adds a transaction log using NESTED propagation.
     * Creates a savepoint within the existing transaction.
     */
    @PostMapping("/nested")
    public ResponseEntity<String> addWithNested(@RequestBody TransactionLogDto dto) {
        log.info("REST request to add transaction log with NESTED propagation");
        String result = transactionLogFacade.addWithNested(dto);
        log.info("NESTED propagation completed successfully");
        return ResponseEntity.ok(result);
    }

    /**
     * Adds a transaction log using SUPPORTS propagation.
     * Participates in transaction if exists, otherwise runs non-transactionally.
     */
    @PostMapping("/supports")
    public ResponseEntity<String> addWithSupports(@RequestBody TransactionLogDto dto) {
        log.info("REST request to add transaction log with SUPPORTS propagation");
        String result = transactionLogFacade.addWithSupports(dto);
        log.info("SUPPORTS propagation completed successfully");
        return ResponseEntity.ok(result);
    }

    /**
     * Adds a transaction log using NOT_SUPPORTED propagation.
     * Always runs non-transactionally, suspending any existing transaction.
     */
    @PostMapping("/not-supported")
    public ResponseEntity<String> addWithNotSupported(@RequestBody TransactionLogDto dto) {
        log.info("REST request to add transaction log with NOT_SUPPORTED propagation");
        String result = transactionLogFacade.addWithNotSupported(dto);
        log.info("NOT_SUPPORTED propagation completed successfully");
        return ResponseEntity.ok(result);
    }

    /**
     * Adds a transaction log using MANDATORY propagation.
     * Requires an existing transaction, throws exception if none exists.
     */
    @PostMapping("/mandatory")
    public ResponseEntity<String> addWithMandatory(@RequestBody TransactionLogDto dto) {
        log.info("REST request to add transaction log with MANDATORY propagation");
        String result = transactionLogFacade.addWithMandatory(dto);
        log.info("MANDATORY propagation completed successfully");
        return ResponseEntity.ok(result);
    }

    /**
     * Adds a transaction log using NEVER propagation.
     * Must run without a transaction, throws exception if one exists.
     */
    @PostMapping("/never")
    public ResponseEntity<String> addWithNever(@RequestBody TransactionLogDto dto) {
        log.info("REST request to add transaction log with NEVER propagation");
        String result = transactionLogFacade.addWithNever(dto);
        log.info("NEVER propagation completed successfully");
        return ResponseEntity.ok(result);
    }
}
