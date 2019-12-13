package exception;

/**
 * Here be dragons !
 * @author: Ezio
 * 2019/12/9 5:40 下午
 *
 */
public class ConnectException extends RuntimeException {

    public ConnectException(String message) {
        super(message);
    }

    public ConnectException(String message, Throwable cause) {
        super(message, cause);
    }
}
