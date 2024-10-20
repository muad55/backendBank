package com.example.Digital_Banking.Service;

import com.example.Digital_Banking.Entities.BankAccount;
import com.example.Digital_Banking.Exceptions.BalanceNotEnoughException;
import com.example.Digital_Banking.Exceptions.BankAccountNotFoundException;
import com.example.Digital_Banking.Exceptions.CustomerNotFoundException;
import com.example.Digital_Banking.dtos.*;

import java.util.List;

public interface BankAccountService {


    CustomerDTO saveCustomer(CustomerDTO customerDto);

    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
     List<CustomerDTO> listCustomers();
     BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
     void debit(String accountId,double amount,String desciption) throws BankAccountNotFoundException, BalanceNotEnoughException;
    void credit(String accountId,double amount,String desciption) throws BankAccountNotFoundException;
    void transfer(String accountIdSource,String accountIdDestination,double amount ) throws BankAccountNotFoundException, BalanceNotEnoughException;

    List<BankAccountDTO> bankAccountList();

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customerDto);

    void deleteCustomer(Long customedId);

    List<OperationDTO> accountHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;

    List<CustomerDTO> searchCustomers(String keyword);
    List<BankAccountDTO> getAccountOfcustomer(Long id);
}
