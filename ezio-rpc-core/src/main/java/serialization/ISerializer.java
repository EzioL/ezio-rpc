package serialization;

/**
 * @creed: Here be dragons !
 * @author: Ezio
 * @Time: 2019/10/24 6:47 下午
 * @desc:
 */
public interface ISerializer {

    <T> byte[] serialize(T t);

    <T> T deserialize(byte[] data, Class<T> clazz);

}
