package com.example.txdemo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entity representing a transaction log entry stored in the {@code transaction_log2} table.
 *
 * <p>Records transaction-related messages along with their creation timestamps,
 * used for auditing and tracking transaction lifecycle events.</p>
 */
@Entity
@Table(name = "transaction_log2")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionLog2 {

    /**
     * Unique identifier for the transaction log entry.
     * Auto-generated using the database identity strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The log message describing the transaction event.
     * Must not be {@code null} and is limited to 500 characters.
     */
    @Column(nullable = false, length = 500)
    private String message;

    /**
     * Timestamp indicating when this log entry was created.
     * Defaults to the current date and time at instantiation. Must not be {@code null}.
     */
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * Constructs a new {@code TransactionLog2} with the specified message.
     * The {@code createdAt} timestamp is automatically set to the current date and time.
     *
     * @param message the log message describing the transaction event; must not be {@code null}
     */
    public TransactionLog2(String message) {
        this.message = message;
        this.createdAt = LocalDateTime.now();
    }
}