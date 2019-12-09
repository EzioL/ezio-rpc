package exception;

/**
 * @creed: Here be dragons !
 * @author: Ezio
 * @Time: 2019/12/9 5:40 下午
 * @desc:
 */
public class RegistryException extends RuntimeException {

    public RegistryException(String message) {
        super(message);
    }

    public RegistryException(String message, Throwable cause) {
        super(message, cause);
    }
}
