package jpabook.japshop.exception;

public class NotEnoughStockException extends RuntimeException{
    // 그냥 오버로이드를 해서 그냥 썻다.
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
