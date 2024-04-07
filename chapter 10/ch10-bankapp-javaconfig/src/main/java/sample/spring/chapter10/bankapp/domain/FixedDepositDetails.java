package sample.spring.chapter10.bankapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "FixedDepositDetails")
@Table(name = "fixed_deposit_details")
public class FixedDepositDetails implements Serializable {

	@Serial
	private static final long serialVersionUID = 437902588438864639L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fixed_deposit_id")
	private int fixedDepositId;

	@ManyToOne
	@JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ACCOUNT_ID", nullable = false)
	private BankAccountDetails bankAccountId;

	@Column(name = "fd_creation_date")
	private Date fdCreationDate;

	@Column(name = "amount")
	private int fdAmount;

	@Column(name = "tenure")
	private int tenure;

	@Column(name = "active")
	private String active;

	@Column(name = "email")
	private String email;
}
