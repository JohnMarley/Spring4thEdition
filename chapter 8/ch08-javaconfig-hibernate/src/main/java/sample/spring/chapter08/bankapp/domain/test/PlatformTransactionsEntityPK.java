package sample.spring.chapter08.bankapp.domain.test;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class PlatformTransactionsEntityPK implements Serializable {

    @Column(name = "request_id")
    private Integer requestId;

    @Column(name = "trading_account_id")
    private Integer tradingAccountId;

    @Column(name = "type_id")
    private Integer typeId;
}
