package pgtest.exception;

/**
 *
 */
public class BusinessException extends RuntimeException {
    public boolean showMessage;
    public BusinessException() {
    }

    public BusinessException(String s, boolean showMessage) {
        super(s);
        this.showMessage = showMessage;
    }

    public BusinessException(String s) {
        super(s);
    }

    public BusinessException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public BusinessException(Throwable throwable) {
        super(throwable);
    }
}
