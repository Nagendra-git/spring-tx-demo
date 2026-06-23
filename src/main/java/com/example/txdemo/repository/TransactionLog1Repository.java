package com.example.txdemo.repository;

import com.example.txdemo.entity.TransactionLog1;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Standard Spring Data JPA repository for {@link TransactionLog1}.
 *
 * <p>Used by the controller to dump the audit trail after running a demo,
 * so you can see exactly which log rows actually made it into MySQL.</p>
 */
public interface TransactionLog1Repository extends JpaRepository<TransactionLog1, Long> {

}
