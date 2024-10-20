package com.example.Digital_Banking.dtos;

import com.example.Digital_Banking.Entities.AccountStatus;
import com.example.Digital_Banking.Entities.Customer;
import com.example.Digital_Banking.Entities.Operation;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.Date;


@Data
public  class SavingBankAccountDTO extends BankAccountDTO{
    private String id;
    private Date createdAt;
    private double balance;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double interestRate;
}
