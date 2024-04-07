package sample.spring.chapter10.bankapp.repository;

import sample.spring.chapter10.bankapp.domain.FixedDepositDetails;

import java.util.List;

public interface FixedDepositRepositoryCustom {
    int createFixedDeposit(FixedDepositDetails fdd);
    FixedDepositDetails getFixedDeposit(int fixedDepositId);
    List<FixedDepositDetails> getInactiveFixedDeposits();
    void setFixedDepositsAsActive(List<FixedDepositDetails> fds);
    List<FixedDepositDetails> findFixedDepositsByBankAccount(int bankAccountId);
}
