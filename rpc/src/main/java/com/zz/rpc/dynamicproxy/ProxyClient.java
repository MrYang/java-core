package com.zz.rpc.dynamicproxy;

import com.zz.common.entity.User;

public class ProxyClient {

    public static void main(String[] args) {
        ProxyHandler proxyHandler = new ProxyHandler();
        UserManager userManager = (UserManager)proxyHandler.newProxyInstance(new UserManagerImpl());
        User user = new User();
        user.setUsername("yxb");
        userManager.addUser(user);
    }
}
