package sample.spring.chapter10.bankapp.repository;

import sample.spring.chapter10.bankapp.domain.BankAccountDetails;

public interface BankAccountRepositoryCustom {
	int createBankAccount(BankAccountDetails bankAccountDetails);
	void subtractFromAccount(int bankAccountId, int amount);
}
