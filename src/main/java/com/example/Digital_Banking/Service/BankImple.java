package com.example.Digital_Banking.Service;


import com.example.Digital_Banking.Entities.*;
import com.example.Digital_Banking.Exceptions.BalanceNotEnoughException;
import com.example.Digital_Banking.Exceptions.BankAccountNotFoundException;
import com.example.Digital_Banking.Exceptions.CustomerNotFoundException;
import com.example.Digital_Banking.Repositories.AccountRepo;
import com.example.Digital_Banking.Repositories.CustomerRepo;
import com.example.Digital_Banking.Repositories.OperationRepo;
import com.example.Digital_Banking.dtos.*;
import com.example.Digital_Banking.mappers.BankAccountMapperImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Transactional
@Slf4j
public class BankImple implements BankAccountService{
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private OperationRepo operationRepo;
    @Autowired
    private BankAccountMapperImpl dtoMapper;

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDto) {
        log.info("Saving new Customer");
        Customer customer=dtoMapper.fromCustomerDTO(customerDto);
        Customer savedCustomer= customerRepo.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepo.findById(customerId).orElse(null);
        if(customer==null)
            throw new CustomerNotFoundException("Customer not found");
        CurrentAccount currentAccount=new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setCustomer(customer);
        currentAccount.setBalance(initialBalance);
        currentAccount.setOverDraft(overDraft);
        CurrentAccount savedBankAccount=accountRepo.save(currentAccount);
        return  dtoMapper.fromCurrentAccount(savedBankAccount);
    }
    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepo.findById(customerId).orElse(null);
        if(customer==null)
            throw new CustomerNotFoundException("Customer not found");
        SavingAccount savingAccount=new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setCustomer(customer);
        savingAccount.setBalance(initialBalance);
        savingAccount.setInterestRate(interestRate);
        SavingAccount savedBankAccount=accountRepo.save(savingAccount);
        return  dtoMapper.fromSavingAccount(savedBankAccount);
    }


    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customers=customerRepo.findAll();
        //je dois transformer la liste de Customer vers CustomerDTO
        /*
        c'est de la programmtion impérative(clasique)
        List<CustomerDTO> customerDTOS=new ArrayList<>();
        for (Customer c:customers){
            CustomerDTO customerDTO=dtoMapper.fromCustomer(c);
            customerDTOS.add(customerDTO);
        }
         */
        //la programmation fonctionnel
        List<CustomerDTO> customerDTOS=customers.stream()
                .map(customer -> dtoMapper.fromCustomer(customer))
                .collect(Collectors.toList());
        return customerDTOS ;
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount=accountRepo.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        if(bankAccount instanceof SavingAccount){
            SavingAccount savingAccount=(SavingAccount) bankAccount;
            return dtoMapper.fromSavingAccount(savingAccount);
        }
        else {
            CurrentAccount currentAccount=(CurrentAccount) bankAccount;
            return dtoMapper.fromCurrentAccount(currentAccount);
        }
    }

    @Override
    public void debit(String accountId, double amount, String desciption) throws BankAccountNotFoundException, BalanceNotEnoughException {
         BankAccount b=accountRepo.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
         if(b.getBalance()<amount) throw new BalanceNotEnoughException("Balance not enough");
         Operation operation=new Operation();
         operation.setType(Optype.DEBIT);
         operation.setOperationDate(new Date());
         operation.setAmount(amount);
         operation.setBankAccount(b);
         operation.setDesciption(desciption);
         operationRepo.save(operation);
         b.setBalance(b.getBalance()-amount);
         accountRepo.save(b);
    }

    @Override
    public void credit(String accountId, double amount, String desciption) throws BankAccountNotFoundException {
        BankAccount b=accountRepo.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        System.out.println(b);
        Operation operation=new Operation();
        operation.setType(Optype.CREDIT);
        operation.setOperationDate(new Date());
        operation.setAmount(amount);
        operation.setBankAccount(b);
        operation.setDesciption(desciption);
        operationRepo.save(operation);
        b.setBalance(b.getBalance()+amount);
        accountRepo.save(b);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotEnoughException {
        debit(accountIdSource,amount,"Transfer to "+accountIdDestination);
        credit(accountIdDestination,amount,"Transfer from "+accountIdSource);
    }
    @Override
    public List<BankAccountDTO> bankAccountList(){
        List<BankAccount> bankAccounts=accountRepo.findAll();
        System.out.println(bankAccounts);
        List<BankAccountDTO> bankAccountDTO = bankAccounts.stream()
                .map(bankAccount -> {
                    if (bankAccount instanceof SavingAccount) {
                        SavingAccount savingAccount = (SavingAccount) bankAccount;
                        return dtoMapper.fromSavingAccount(savingAccount);
                    } else {
                        CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                        return dtoMapper.fromCurrentAccount(currentAccount);
                    }
                }).collect(Collectors.toList());
        return bankAccountDTO;
    }
    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
       Customer customer= customerRepo.findById(customerId)
               .orElseThrow(()->new CustomerNotFoundException("Customer not found"));
       return dtoMapper.fromCustomer(customer);
    }
    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDto) {
        log.info("Saving new Customer");
        Customer customer=dtoMapper.fromCustomerDTO(customerDto);
        Customer savedCustomer= customerRepo.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }
    @Override
    public void deleteCustomer(Long customedId){
        customerRepo.deleteById(customedId); ;
    }
    @Override
    public List<OperationDTO> accountHistory(String accountId) {
        List<Operation> accountOperations = operationRepo.findByBankAccountId(accountId);
        return accountOperations.stream()
                .map(operation -> dtoMapper.fromAccountOperation(operation))
                .collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount=accountRepo.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        Page<Operation> accountOperations = operationRepo.findByBankAccountId(accountId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
        accountHistoryDTO.setOperationDTOList(accountOperations.getContent().stream().map(operation -> dtoMapper.fromAccountOperation(operation)).collect(Collectors.toList()));
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }

    @Override
    public List<CustomerDTO> searchCustomers(String keyword) {
        List<Customer> customers=customerRepo.findByNameContains(keyword);
        List<CustomerDTO> customerDTOS = customers.stream().map(customer -> dtoMapper.fromCustomer(customer)).collect(Collectors.toList());
        return customerDTOS;
    }

    @Override
    public List<BankAccountDTO> getAccountOfcustomer(Long id) {
        List<BankAccount> accounts=accountRepo.findByCustomerId(id);
        List<BankAccountDTO> bankAccountDTO = accounts.stream()
                .map(bankAccount -> {
                    if (bankAccount instanceof SavingAccount) {
                        SavingAccount savingAccount = (SavingAccount) bankAccount;
                        return dtoMapper.fromSavingAccount(savingAccount);
                    } else {
                        CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                        return dtoMapper.fromCurrentAccount(currentAccount);
                    }
                }).collect(Collectors.toList());
        System.out.print(bankAccountDTO);
        return bankAccountDTO;
    }


}
