package sample.spring.chapter10.bankapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;
import sample.spring.chapter10.bankapp.domain.BankAccountDetails;
import sample.spring.chapter10.bankapp.exceptions.NoBankAccountFoundException;

@Repository(value = "bankAccountRepositoryCustom")
public class BankAccountRepositoryCustomImpl implements BankAccountRepositoryCustom {
//	private SimpleJdbcInsert insertBankAccountDetail;

//	@Autowired
//	private JdbcTemplate jdbcTemplate;

//	@Autowired
//	private void setDataSource(DataSource dataSource) {
//		this.insertBankAccountDetail = new SimpleJdbcInsert(dataSource)
//				.withTableName("bank_account_details")
//				.usingGeneratedKeyColumns("bank_account_id");
//	}

	@Autowired
	private BankAccountRepository bankAccountRepository;

	@Override
	@Transactional(transactionManager = "dbTxManager")
	public int createBankAccount(final BankAccountDetails bankAccountDetails) {
//		Map<String, Object> parameters = new HashMap<String, Object>(2);
//		parameters.put("balance_amount", bankAccountDetails.getBalanceAmount());
//		parameters.put("last_transaction_ts", new java.sql.Date(
//				bankAccountDetails.getLastTransactionTimestamp().getTime()));
//		Number key = insertBankAccountDetail.executeAndReturnKey(parameters);
//		return key.intValue();
		return bankAccountRepository.save(bankAccountDetails).getAccountId();
	}

	public void subtractFromAccount(int bankAccountId, int amount) {
//		jdbcTemplate
//				.update("update bank_account_details set balance_amount = ? where account_id = ?",
//						amount, bankAccountId);
		// TODO upd not to just save amount to bank account but deduct deposit amount from account
		// and throw exception if not enough amount on account
		var bankAccount = bankAccountRepository.findById(bankAccountId)
				.orElseThrow(() -> new NoBankAccountFoundException("no bank account found by id =" + bankAccountId));
		bankAccount.setBalanceAmount(amount);
		bankAccountRepository.save(bankAccount);
	}
}
