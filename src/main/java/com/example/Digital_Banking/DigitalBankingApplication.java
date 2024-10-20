package com.example.Digital_Banking;

import com.example.Digital_Banking.Entities.*;
import com.example.Digital_Banking.Repositories.AccountRepo;
import com.example.Digital_Banking.Repositories.CustomerRepo;
import com.example.Digital_Banking.Repositories.OperationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;


@SpringBootApplication
public class DigitalBankingApplication implements CommandLineRunner {
    @Autowired
	AccountRepo accountRepo;
	@Autowired
	CustomerRepo customerRepo;
	@Autowired
	OperationRepo operationRepo;
	public static void main(String[] args) {
		SpringApplication.run(DigitalBankingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Stream.of("Hassan","Yassine","Aicha").forEach(name->{
			Customer customer=new Customer();
			customer.setName(name);
			customer.setEmail(name+"@gmail.com");
			customerRepo.save(customer);
		});
		customerRepo.findAll().forEach(c->{
			CurrentAccount currentAccount=new CurrentAccount();
			currentAccount.setId(UUID.randomUUID().toString());
			currentAccount.setBalance(Math.random()*90000);
			currentAccount.setCustomer(c);
			currentAccount.setCreatedAt(new Date());
			currentAccount.setStatus(AccountStatus.CREATED);
			currentAccount.setOverDraft(9000);

			accountRepo.save(currentAccount);

			SavingAccount savingAccount=new SavingAccount();
			savingAccount.setId(UUID.randomUUID().toString());
			savingAccount.setBalance(Math.random()*90000);
			savingAccount.setCustomer(c);
			savingAccount.setCreatedAt(new Date());
			savingAccount.setStatus(AccountStatus.CREATED);
			savingAccount.setInterestRate(5.5);

			accountRepo.save(savingAccount);
		});
		accountRepo.findAll().forEach(a->{
			for (int i=0;i<10;i++){
				Operation op=new Operation();
				op.setBankAccount(a);
				op.setOperationDate(new Date());
				op.setAmount(Math.random()*12000);
				op.setType(Math.random()>0.5 ? Optype.DEBIT : Optype.CREDIT );
				operationRepo.save(op);
			}
		});
	}
}
