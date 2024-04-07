package sample.spring.chapter10.bankapp.exceptions;

import java.io.Serial;

public class NoFixedDepositFoundException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 437902588438864637L;

	public NoFixedDepositFoundException(String msg) {
		super(msg);
	}
}
