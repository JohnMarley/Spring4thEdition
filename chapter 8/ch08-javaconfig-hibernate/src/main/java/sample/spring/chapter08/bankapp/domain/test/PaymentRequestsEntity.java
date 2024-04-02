package sample.spring.chapter08.bankapp.domain.test;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "payment_requests")
@Data
public class PaymentRequestsEntity {

    @Id
    @Column(name = "request_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer requestId;

    @OneToMany(mappedBy = "paymentRequestsEntity")
    private Set<PlatformTransactionsEntity> platformTransactionsEntitySet;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "tradnig_account_id")
    private Integer tradingAccountId;
}
