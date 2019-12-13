import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Here be dragons !
 * @author: Ezio
 * 2019/12/11 5:39 下午
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private int id;

    private String name;
}
