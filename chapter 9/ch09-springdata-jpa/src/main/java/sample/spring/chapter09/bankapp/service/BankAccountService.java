package sample.spring.chapter09.bankapp.service;

import sample.spring.chapter09.bankapp.domain.BankAccountDetails;

import java.util.Optional;

public interface BankAccountService {
	int createBankAccount(BankAccountDetails bankAccountDetails);
	Optional<BankAccountDetails> getBankAccountById(int accountId);
}
