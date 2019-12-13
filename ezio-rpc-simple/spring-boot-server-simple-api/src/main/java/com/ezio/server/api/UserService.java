package com.ezio.server.api;

import com.ezio.server.dto.UserDTO;

import java.util.List;

/**
 * Here be dragons !
 * @author: Ezio
 * 2019/12/6 7:13 下午
 *
 */
public interface UserService {

    UserDTO getById(int userId);

    List<UserDTO> getInIds(List<Integer> userIds);

}
