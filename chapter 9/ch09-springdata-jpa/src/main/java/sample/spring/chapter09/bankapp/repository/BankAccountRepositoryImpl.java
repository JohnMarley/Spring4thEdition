package sample.spring.chapter09.bankapp.repository;




import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import sample.spring.chapter09.bankapp.domain.BankAccountDetails;

import java.util.NoSuchElementException;

//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;

public class BankAccountRepositoryImpl implements BankAccountRepositoryCustom {

	@Autowired
	BankAccountRepository bankAccountRepository;

	@Override
	public void subtractFromAccount(int bankAccountId, int amount) {
		var bankAccountDetails = bankAccountRepository
				.findById(bankAccountId)
				.orElseThrow(()-> new NoSuchElementException("no bankAccount registered with id " + bankAccountId));
		if (bankAccountDetails.getBalanceAmount() < amount) {
			throw new RuntimeException("Insufficient balance amount in bank account");
		}
		bankAccountDetails.setBalanceAmount(bankAccountDetails.getBalanceAmount() - amount);
		bankAccountRepository.save(bankAccountDetails);
	}
//	@PersistenceContext
//	private EntityManager entityManager;
//
//	@Override
//	public void subtractFromAccount(int bankAccountId, int amount) {
//		BankAccountDetails bankAccountDetails = entityManager.find(BankAccountDetails.class, bankAccountId);
//		if (bankAccountDetails.getBalanceAmount() < amount) {
//			throw new RuntimeException("Insufficient balance amount in bank account");
//		}
//		bankAccountDetails.setBalanceAmount(bankAccountDetails.getBalanceAmount() - amount);
//		entityManager.merge(bankAccountDetails);
//	}
}
