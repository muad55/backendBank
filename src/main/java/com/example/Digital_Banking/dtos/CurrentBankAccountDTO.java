package com.example.Digital_Banking.dtos;

import com.example.Digital_Banking.Entities.AccountStatus;
import lombok.Data;

import java.util.Date;


@Data
public  class CurrentBankAccountDTO extends BankAccountDTO{
    private String id;
    private Date createdAt;
    private double balance;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double overDraft;
}
