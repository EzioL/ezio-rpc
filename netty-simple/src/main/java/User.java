import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @creed: Here be dragons !
 * @author: Ezio
 * @Time: 2019/12/11 5:39 下午
 * @desc:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private int id;

    private String name;
}
