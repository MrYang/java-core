package com.zz.rpc.dynamicproxy;

import com.zz.common.entity.User;

public class UserManagerImpl implements UserManager {

    @Override
    public void addUser(User user) {
        System.out.println("add user");
    }
}
