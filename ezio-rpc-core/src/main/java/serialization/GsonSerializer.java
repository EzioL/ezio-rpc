package serialization;

/**
 * @creed: Here be dragons !
 * @author: Ezio
 * @Time: 2019-10-24 19:07
 * @desc:
 */
public class GsonSerializer implements ISerializer {

    public <T> byte[] serialize(T t) {

        return new GsonSerializer().serialize(t);
    }

    public <T> T deserialize(byte[] data, Class<T> clazz) {

        return new GsonSerializer().deserialize(data, clazz);
    }


}
