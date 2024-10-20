package com.example.Digital_Banking.Repositories;

import com.example.Digital_Banking.Entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface AccountRepo extends JpaRepository<BankAccount,String> {
    List<BankAccount> findByCustomerId(Long id);
}
