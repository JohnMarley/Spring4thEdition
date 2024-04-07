package sample.spring.chapter10.bankapp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sample.spring.chapter10.bankapp.domain.BankAccountDetails;
import sample.spring.chapter10.bankapp.domain.FixedDepositDetails;

@Repository
@Qualifier("dbTxManager")
public interface FixedDepositRepository extends JpaRepository<FixedDepositDetails, Integer>, FixedDepositRepositoryCustom {

    List<FixedDepositDetails> findByActive(String active);
    List<FixedDepositDetails> findByBankAccountId(BankAccountDetails bankAccountId);
}
