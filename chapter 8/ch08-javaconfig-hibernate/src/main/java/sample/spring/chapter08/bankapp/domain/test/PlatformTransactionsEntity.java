package sample.spring.chapter08.bankapp.domain.test;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "platform_transactions")
public class PlatformTransactionsEntity {

    @EmbeddedId
    private PlatformTransactionsEntityPK id;

    @ManyToOne
    @JoinColumn(name = "type_id", insertable = false, updatable = false)
    private DepositTransactionTypesEntity depositTransactionTypesEntity;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "request_id", referencedColumnName = "request_id", insertable = false, updatable = false),
            @JoinColumn(name = "trading_account_id", referencedColumnName = "trading_account_id", insertable = false, updatable = false)
    })
    private PaymentRequestsEntity paymentRequestsEntity;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "comments")
    private String comments;

    @Column(name = "currency")
    private String currency;
}
