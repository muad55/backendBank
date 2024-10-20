package com.example.Digital_Banking.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Operation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date OperationDate;
    private double amount;
    @Enumerated(EnumType.STRING)
    private Optype type;
    private String desciption;
    @ManyToOne
    private BankAccount bankAccount;
}
