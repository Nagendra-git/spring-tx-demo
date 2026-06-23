package com.example.txdemo.facade;

import com.example.txdemo.dto.TransactionLogDto;

public interface TransactionLogFacade {

    String addWithRequired(TransactionLogDto dto);

    String addWithRequiresNew(TransactionLogDto dto);

    String addWithNested(TransactionLogDto dto);

    String addWithSupports(TransactionLogDto dto);

    String addWithNotSupported(TransactionLogDto dto);

    String addWithMandatory(TransactionLogDto dto);

    String addWithNever(TransactionLogDto dto);
}
