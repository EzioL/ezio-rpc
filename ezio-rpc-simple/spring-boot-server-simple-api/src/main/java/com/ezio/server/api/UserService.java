package com.ezio.server.api;

import com.ezio.server.dto.UserDTO;

import java.util.List;

/**
 * @creed: Here be dragons !
 * @author: Ezio
 * @Time: 2019/12/6 7:13 下午
 * @desc:
 */
public interface UserService {

    UserDTO getById(int userId);

    List<UserDTO> getInIds(List<Integer> userIds);

}
