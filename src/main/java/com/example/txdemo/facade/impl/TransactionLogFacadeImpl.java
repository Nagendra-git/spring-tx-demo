package com.example.txdemo.facade.impl;

import com.example.txdemo.dto.TransactionLogDto;
import com.example.txdemo.facade.TransactionLogFacade;
import com.example.txdemo.service.TransactionLog1Service;
import com.example.txdemo.service.TransactionLog2Service;
import com.example.txdemo.utils.Facade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Facade implementation for managing transaction log operations across multiple data sources.
 *
 * <p>Coordinates the persistence of transaction log entries into two separate log stores
 * ({@code TransactionLog1} and {@code TransactionLog2}) within a single transactional boundary.
 * If either service fails, the entire transaction is rolled back to maintain data consistency.</p>
 */
@Facade
@RequiredArgsConstructor
@Slf4j
public class TransactionLogFacadeImpl implements TransactionLogFacade {

    private final TransactionLog1Service transactionLog1Service;
    private final TransactionLog2Service transactionLog2Service;

    /**
     * Persists logs using REQUIRED propagation.
     * Joins existing transaction or creates a new one.
     * Full rollback occurs if either service fails.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String addWithRequired(TransactionLogDto dto) {
        log.info("[REQUIRED] Starting transaction log persistence");
        transactionLog1Service.addTransactionLog(dto);
        log.info("[REQUIRED] TransactionLog1 saved successfully");
        transactionLog2Service.addTransactionLog(dto);
        log.info("[REQUIRED] TransactionLog2 saved successfully");
        return "REQUIRED: TransactionLog added successfully";
    }

    /**
     * Persists logs using REQUIRES_NEW propagation.
     * Suspends existing transaction and creates a new independent one.
     * Each service operates in its own transaction scope.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public String addWithRequiresNew(TransactionLogDto dto) {
        log.info("[REQUIRES_NEW] Suspending existing transaction, starting new one");
        transactionLog1Service.addTransactionLog(dto);
        log.info("[REQUIRES_NEW] TransactionLog1 saved successfully");
        transactionLog2Service.addTransactionLog(dto);
        log.info("[REQUIRES_NEW] TransactionLog2 saved successfully");
        return "REQUIRES_NEW: TransactionLog added successfully";
    }

    /**
     * Persists logs using NESTED propagation.
     * Creates a savepoint within the outer transaction.
     * Inner failure rolls back to savepoint only, not the entire transaction.
     */
    @Transactional(propagation = Propagation.NESTED)
    @Override
    public String addWithNested(TransactionLogDto dto) {
        log.info("[NESTED] Creating savepoint within existing transaction");
        transactionLog1Service.addTransactionLog(dto);
        log.info("[NESTED] TransactionLog1 saved, savepoint created");
        transactionLog2Service.addTransactionLog(dto);
        log.info("[NESTED] TransactionLog2 saved successfully");
        return "NESTED: TransactionLog added successfully";
    }

    /**
     * Persists logs using SUPPORTS propagation.
     * Participates in existing transaction if present, otherwise runs non-transactionally.
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public String addWithSupports(TransactionLogDto dto) {
        log.info("[SUPPORTS] Running within existing transaction if available");
        transactionLog1Service.addTransactionLog(dto);
        log.info("[SUPPORTS] TransactionLog1 saved successfully");
        transactionLog2Service.addTransactionLog(dto);
        log.info("[SUPPORTS] TransactionLog2 saved successfully");
        return "SUPPORTS: TransactionLog added successfully";
    }

    /**
     * Persists logs using NOT_SUPPORTED propagation.
     * Suspends existing transaction and runs non-transactionally.
     * No rollback support — each save is immediately committed.
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public String addWithNotSupported(TransactionLogDto dto) {
        log.info("[NOT_SUPPORTED] Suspending transaction, running non-transactionally");
        transactionLog1Service.addTransactionLog(dto);
        log.info("[NOT_SUPPORTED] TransactionLog1 saved (no rollback possible)");
        transactionLog2Service.addTransactionLog(dto);
        log.info("[NOT_SUPPORTED] TransactionLog2 saved (no rollback possible)");
        return "NOT_SUPPORTED: TransactionLog added successfully";
    }

    /**
     * Persists logs using MANDATORY propagation.
     * Requires an active transaction from the caller.
     * Throws {@link org.springframework.transaction.IllegalTransactionStateException}
     * if no active transaction exists.
     */
    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public String addWithMandatory(TransactionLogDto dto) {
        log.info("[MANDATORY] Joining mandatory existing transaction");
        transactionLog1Service.addTransactionLog(dto);
        log.info("[MANDATORY] TransactionLog1 saved successfully");
        transactionLog2Service.addTransactionLog(dto);
        log.info("[MANDATORY] TransactionLog2 saved successfully");
        return "MANDATORY: TransactionLog added successfully";
    }

    /**
     * Persists logs using NEVER propagation.
     * Must be executed without any active transaction.
     * Throws {@link org.springframework.transaction.IllegalTransactionStateException}
     * if an active transaction is detected.
     */
    @Transactional(propagation = Propagation.NEVER)
    @Override
    public String addWithNever(TransactionLogDto dto) {
        log.info("[NEVER] Running strictly without any transaction");
        transactionLog1Service.addTransactionLog(dto);
        log.info("[NEVER] TransactionLog1 saved successfully");
        transactionLog2Service.addTransactionLog(dto);
        log.info("[NEVER] TransactionLog2 saved successfully");
        return "NEVER: TransactionLog added successfully";
    }
}
