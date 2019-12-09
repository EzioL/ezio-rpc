package exception;

/**
 * @creed: Here be dragons !
 * @author: Ezio
 * @Time: 2019/12/9 5:40 下午
 * @desc:
 */
public class ConnectException extends RuntimeException {

    public ConnectException(String message) {
        super(message);
    }

    public ConnectException(String message, Throwable cause) {
        super(message, cause);
    }
}
