package com.example.txdemo.dto;

import lombok.Data;


/**
 * Data Transfer Object representing a transaction log entry.
 *
 * <p>Encapsulates log messages associated with a transaction,
 * typically used for auditing, debugging, or tracking transaction events.</p>
 */
@Data
public class TransactionLogDto {

    /** Primary log message describing the transaction event. */
    private String message1;

    /** Secondary log message providing additional transaction details. */
    private String message2;
}
