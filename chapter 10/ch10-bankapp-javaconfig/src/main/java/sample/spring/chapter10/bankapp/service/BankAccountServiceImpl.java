package sample.spring.chapter10.bankapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sample.spring.chapter10.bankapp.repository.BankAccountRepository;
import sample.spring.chapter10.bankapp.domain.BankAccountDetails;

@Service(value = "bankAccountService")
public class BankAccountServiceImpl implements BankAccountService {

	@Autowired
	private BankAccountRepository bankAccountRepository;

	@Override
	public int createBankAccount(BankAccountDetails bankAccountDetails) {
		return bankAccountRepository.createBankAccount(bankAccountDetails);
	}

}
