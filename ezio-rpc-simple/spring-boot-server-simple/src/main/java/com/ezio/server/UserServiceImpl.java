package com.ezio.server;

import annotation.RpcService;
import com.ezio.server.api.UserService;
import com.ezio.server.dto.UserDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @creed: Here be dragons !
 * @author: Ezio
 * @Time: 2019/12/9 7:22 下午
 * @desc:
 */
@RpcService(value = UserService.class)
public class UserServiceImpl implements UserService {

    String[] names = new String[]{"张三", "李四", "王五"};

    @Override
    public UserDTO getById(int userId) {

        return new UserDTO(userId, names[userId % 3], "creed");
    }

    @Override
    public List<UserDTO> getInIds(List<Integer> userIds) {
        return userIds.stream().map(this::getById).collect(Collectors.toList());
    }
}
