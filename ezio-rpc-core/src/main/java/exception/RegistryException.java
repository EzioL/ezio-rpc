package exception;

/**
 * Here be dragons !
 * @author: Ezio
 * 2019/12/9 5:40 下午
 *
 */
public class RegistryException extends RuntimeException {

    public RegistryException(String message) {
        super(message);
    }

    public RegistryException(String message, Throwable cause) {
        super(message, cause);
    }
}
