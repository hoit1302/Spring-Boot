package jpabook.jpashop.exception;

public class NotEnoughStockException extends RuntimeException {
	// override 하는 이유: message를 넘겨 예외 발생한 근원적 exception 까지 trace하기 위해서.
	public NotEnoughStockException() {
		super();
	}

	public NotEnoughStockException(String message) {
		super(message);
	}

	public NotEnoughStockException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotEnoughStockException(Throwable cause) {
		super(cause);
	}
}
