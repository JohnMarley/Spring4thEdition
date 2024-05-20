package sample.spring.chapter10.bankapp.exceptions;

import java.io.Serial;

public class NoEnoughAccountBalanceException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 437902588438864637L;

	public NoEnoughAccountBalanceException(String msg) {
		super(msg);
	}
}
