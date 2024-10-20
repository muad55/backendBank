package com.example.Digital_Banking.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
@Data @AllArgsConstructor @NoArgsConstructor
@Entity
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY)
    //quand je consulte un client c'est pas besoin de consulter les comptes
    //quand tu arrive a cette attribut la c'est pas la peine de serialiser cette attribut
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<BankAccount> bankAccounts;
}
