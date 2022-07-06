package com.midash.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.midash.bank.dto.MoneyTransferRequest;
import com.midash.bank.dto.MoneyTransferResponse;
import com.midash.bank.service.TransactionService;

@Controller
@RequestMapping("transaction")
@CrossOrigin(origins = "*", maxAge=3600, methods={
    RequestMethod.GET,
    RequestMethod.POST,
    RequestMethod.PUT,
    RequestMethod.DELETE,
    RequestMethod.OPTIONS,
    RequestMethod.HEAD
})
public class TransactionServiceController {
    
    TransactionService transactionService;

    public TransactionServiceController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("transfer")
    public ResponseEntity<MoneyTransferResponse> transfer(@RequestBody MoneyTransferRequest request) {
        transactionService.transfer(request.sourceAccountId, request.targetAccountId, request.amount);
        double sourceAccountBalance = transactionService.findById(request.sourceAccountId).balance;
        double targetAccountBalance = transactionService.findById(request.targetAccountId).balance;

        return ResponseEntity.ok().body(
            MoneyTransferResponse.builder()
            .sourceAccountBalance(sourceAccountBalance)
            .targetAccountBalance(targetAccountBalance)
            .targetAccountId(request.targetAccountId)
            .sourceAccountId(request.targetAccountId)
            .build()
        );
    }
}
