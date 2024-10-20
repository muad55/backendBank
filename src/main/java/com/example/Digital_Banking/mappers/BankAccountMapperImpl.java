package com.example.Digital_Banking.mappers;

import com.example.Digital_Banking.Entities.CurrentAccount;
import com.example.Digital_Banking.Entities.Customer;
import com.example.Digital_Banking.Entities.Operation;
import com.example.Digital_Banking.Entities.SavingAccount;
import com.example.Digital_Banking.dtos.CurrentBankAccountDTO;
import com.example.Digital_Banking.dtos.CustomerDTO;
import com.example.Digital_Banking.dtos.OperationDTO;
import com.example.Digital_Banking.dtos.SavingBankAccountDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


@Service
public class BankAccountMapperImpl {
    public CustomerDTO fromCustomer(Customer customer){
       CustomerDTO customerDTO=new CustomerDTO();
       //il permet de copier les propriétes d'un objet vers un autre
       BeanUtils.copyProperties(customer,customerDTO);
       //customerDTO.setId(customer.getId());
       //customerDTO.setName(customer.getName());
       //customerDTO.setEmail(customer.getEmail());
       return customerDTO;
    }
    public Customer fromCustomerDTO(CustomerDTO customerDTO){
        Customer customer=new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        return customer;
    }

    public SavingBankAccountDTO fromSavingAccount(SavingAccount savingAccount){
        SavingBankAccountDTO savingBankAccountDTO=new SavingBankAccountDTO();
        BeanUtils.copyProperties(savingAccount,savingBankAccountDTO);
        savingBankAccountDTO.setCustomerDTO(fromCustomer(savingAccount.getCustomer()));
        savingBankAccountDTO.setType(savingAccount.getClass().getSimpleName());
        return savingBankAccountDTO;
    }
    public SavingAccount fromSavingAccountDTO(SavingBankAccountDTO savingBankAccountDTO){
        SavingAccount savingAccount=new SavingAccount();
        BeanUtils.copyProperties(savingBankAccountDTO,savingAccount);
        savingAccount.setCustomer(fromCustomerDTO(savingBankAccountDTO.getCustomerDTO()));
        return savingAccount;
    }
    public CurrentBankAccountDTO fromCurrentAccount(CurrentAccount currentAccount){
        CurrentBankAccountDTO currentBankAccountDTO=new CurrentBankAccountDTO();
        BeanUtils.copyProperties(currentAccount,currentBankAccountDTO);
        currentBankAccountDTO.setCustomerDTO(fromCustomer(currentAccount.getCustomer()));
        currentBankAccountDTO.setType(currentAccount.getClass().getSimpleName());
        return currentBankAccountDTO;
    }
    public CurrentAccount fromCurrentAccountDTO(CurrentBankAccountDTO currentBankAccountDTO){
        CurrentAccount currentAccount=new CurrentAccount();
        BeanUtils.copyProperties(currentBankAccountDTO,currentAccount);
        currentAccount.setCustomer(fromCustomerDTO(currentBankAccountDTO.getCustomerDTO()));
        return currentAccount;
    }
    public OperationDTO fromAccountOperation(Operation operation){
        OperationDTO operationDTO=new OperationDTO();
        BeanUtils.copyProperties(operation,operationDTO);
        return operationDTO;
    }

}
