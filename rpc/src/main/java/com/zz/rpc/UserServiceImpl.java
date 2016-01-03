package com.zz.rpc;

import com.zz.common.entity.User;

public class UserServiceImpl implements UserService {
    @Override
    public void addUser(User user) {
        System.out.println(user.getUsername());
    }
}
