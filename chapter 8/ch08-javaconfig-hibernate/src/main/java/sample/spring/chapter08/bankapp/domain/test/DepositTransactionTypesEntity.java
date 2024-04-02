package sample.spring.chapter08.bankapp.domain.test;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "deposit_transaction_types")
@Data
public class DepositTransactionTypesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "depositTransactionTypesEntity")
    private Set<PlatformTransactionsEntity> platformTransactionsEntitySet;
}
