package com.zz.rpc;

public class MultiRpcProvider {

    public static void main(String[] args) throws Exception {
        HelloService helloService = new HelloServiceImpl();
        RpcFramework.registerService(helloService, HelloService.class.getName());

        UserService userService = new UserServiceImpl();
        RpcFramework.registerService(userService, UserService.class.getName());

        RpcFramework.startMultiService(9527);
    }
}
