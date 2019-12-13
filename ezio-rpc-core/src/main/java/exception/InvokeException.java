package exception;

/**
 * Here be dragons !
 * @author: Ezio
 * 2019/12/9 5:40 下午
 *
 */
public class InvokeException extends RuntimeException {

    public InvokeException(String message) {
        super(message);
    }

    public InvokeException(String message, Throwable cause) {
        super(message, cause);
    }
}
