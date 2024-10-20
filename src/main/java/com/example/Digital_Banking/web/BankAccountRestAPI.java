package com.example.Digital_Banking.web;


import com.example.Digital_Banking.Entities.BankAccount;
import com.example.Digital_Banking.Entities.Operation;
import com.example.Digital_Banking.Exceptions.BalanceNotEnoughException;
import com.example.Digital_Banking.Exceptions.BankAccountNotFoundException;
import com.example.Digital_Banking.Service.BankAccountService;
import com.example.Digital_Banking.dtos.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class BankAccountRestAPI {
    private BankAccountService bankAccountService;

    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
          return bankAccountService.getBankAccount(accountId);
    }
    @GetMapping("/accounts")
    public List<BankAccountDTO> listAccounts(){
          return bankAccountService.bankAccountList();
    }
    @GetMapping("/accounts/{accountId}/operations")
    public List<OperationDTO> getHistory(@PathVariable String accountId){
       return bankAccountService.accountHistory(accountId);
    }

    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId, @RequestParam(name="page",defaultValue = "0") int page, @RequestParam(name="size",defaultValue = "5") int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId,page,size);
    }
    @PostMapping("/accounts/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BalanceNotEnoughException, BankAccountNotFoundException {
        this.bankAccountService.debit(debitDTO.getAccountId(),debitDTO.getAmount(),debitDTO.getDescription());
        return debitDTO;
    }
    @PostMapping("/accounts/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BalanceNotEnoughException, BankAccountNotFoundException {
        this.bankAccountService.credit(creditDTO.getAccountId(),creditDTO.getAmount(),creditDTO.getDescription());
        return creditDTO;
    }
    @PostMapping("/accounts/transfer")
    public void transfer(@RequestBody TransferDTO transferDTO) throws BalanceNotEnoughException, BankAccountNotFoundException {
        this.bankAccountService.transfer(transferDTO.getAccountSource(),transferDTO.getAccountDestination(),transferDTO.getAmount());
    }
    @GetMapping("/accounts/customers/{customerId}")
    public List<BankAccountDTO> accounts(@PathVariable Long customerId){
        return this.bankAccountService.getAccountOfcustomer(customerId);
    }

}
