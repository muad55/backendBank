package com.example.Digital_Banking.dtos;

import com.example.Digital_Banking.Entities.BankAccount;
import com.example.Digital_Banking.Entities.Optype;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
public class OperationDTO {
    private Long id;
    private Date OperationDate;
    private double amount;
    private Optype type;
    private String desciption;
}
