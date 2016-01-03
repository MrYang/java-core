package com.zz.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class RpcFramework {

    private static Map<String, Object> serviceMap = new HashMap<>();

    public static void registerService(Object service, String serviceName) throws Exception {
        if (service == null) {
            throw new IllegalArgumentException("service instance == null");
        }
        if (!serviceMap.containsKey(serviceName)) {
            serviceMap.put(serviceName, service);
            System.out.println("Register service " + serviceName);
        } else {
            System.out.println("service instance had exists");
        }
    }

    /**
     * 启动服务
     *
     * @param port 端口
     * @throws Exception
     */
    public static void startMultiService(int port) throws Exception {
        if (port <= 0 || port > 65535) {
            throw new IllegalArgumentException("Invalid port " + port);
        }

        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            try (final Socket socket = serverSocket.accept()) {
                new Thread(() -> {
                    try (ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                         ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream())) {
                        String serviceName = input.readUTF();
                        String methodName = input.readUTF();
                        Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
                        Object[] arguments = (Object[]) input.readObject();

                        Object service = serviceMap.get(serviceName);
                        System.out.println(service == null ? "服务为空" : "call service" + service.getClass().getName());
                        Method method = service.getClass().getMethod(methodName, parameterTypes);
                        Object result = method.invoke(service, arguments);
                        output.writeObject(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }
    }

    /**
     * 引用服务(多服务接口)
     *
     * @param <T>            接口泛型
     * @param interfaceClass 接口类型
     * @param host           服务器主机名
     * @param port           服务器端口
     * @return 远程服务
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T refer4MultiService(Class<T> interfaceClass, String host, int port) throws Exception {
        if (interfaceClass == null) {
            throw new IllegalArgumentException("Interface class == null");
        }
        if (!interfaceClass.isInterface()) {
            throw new IllegalArgumentException("The " + interfaceClass.getName() + " must be interface class!");
        }
        if (host == null || host.length() == 0) {
            throw new IllegalArgumentException("Host == null!");
        }
        if (port <= 0 || port > 65535) {
            throw new IllegalArgumentException("Invalid port " + port);
        }

        System.out.println("get remote service " + interfaceClass.getName() + "from server " + host + ":" + port);

        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass}, (proxy, method, args) -> {
            Socket socket = new Socket(host, port);
            try (ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {
                output.writeUTF(interfaceClass.getName());
                output.writeUTF(method.getName());
                output.writeObject(method.getParameterTypes());
                output.writeObject(args);
                Object result = input.readObject();
                if (result instanceof Throwable) {
                    throw (Throwable) result;
                }
                return result;
            }
        });
    }

}
