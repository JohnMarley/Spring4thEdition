package sample.spring.chapter10.bankapp.exceptions;

import java.io.Serial;

public class NoBankAccountFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 437902588438864638L;

    public NoBankAccountFoundException(String msg) {
        super(msg);
    }
}
