package com.ezio.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @creed: Here be dragons !
 * @author: Ezio
 * @Time: 2019/12/6 7:14 下午
 * @desc:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Integer id;

    private String name;

    private String creed;

}
