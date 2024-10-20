package com.example.Digital_Banking.Repositories;

import com.example.Digital_Banking.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Long> {

    List<Customer> findByNameContains(String keyword);
}
