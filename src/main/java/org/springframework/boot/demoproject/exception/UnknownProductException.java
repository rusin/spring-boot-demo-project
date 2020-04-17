package org.springframework.boot.demoproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Unknow product privided in order!")
public class UnknownProductException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnknownProductException(String message) {
        super(message);
    }
}
