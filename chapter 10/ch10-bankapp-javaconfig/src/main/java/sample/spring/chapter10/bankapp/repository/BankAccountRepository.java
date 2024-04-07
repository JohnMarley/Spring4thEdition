package sample.spring.chapter10.bankapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sample.spring.chapter10.bankapp.domain.BankAccountDetails;

public interface BankAccountRepository extends JpaRepository<BankAccountDetails, Integer>, BankAccountRepositoryCustom {
}
