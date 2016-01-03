package com.zz.rpc;

import com.zz.common.entity.User;

import java.util.Date;

public class MultiRpcConsumer {

    public static void main(String[] args) throws Exception {
        HelloService helloService = RpcFramework.refer4MultiService(HelloService.class, "127.0.0.1", 9527);

        helloService.hello("yxb");

        UserService userService = RpcFramework.refer4MultiService(UserService.class, "127.0.0.1", 9527);
        User user = new User("yxb", new Date());
        userService.addUser(user);
    }
}