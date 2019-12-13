package com.ezio.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Here be dragons !
 * @author: Ezio
 * 2019/12/6 7:14 下午
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    private Integer id;

    private String name;

    private String creed;

}
