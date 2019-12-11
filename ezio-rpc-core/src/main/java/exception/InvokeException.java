package exception;

/**
 * @creed: Here be dragons !
 * @author: Ezio
 * @Time: 2019/12/9 5:40 下午
 * @desc:
 */
public class InvokeException extends RuntimeException {

    public InvokeException(String message) {
        super(message);
    }

    public InvokeException(String message, Throwable cause) {
        super(message, cause);
    }
}
