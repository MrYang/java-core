package com.zz.socket.tranobject;

import com.zz.common.entity.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BlockTranObjectServer {

    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        Executor pool = Executors.newCachedThreadPool();
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                try (final Socket socket = serverSocket.accept()) {
                    Runnable r = () -> handleRequest(socket);
                    pool.execute(r);
                }
            }
        }
    }

    private static void handleRequest(Socket socket) {
        try (ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
             ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream())) {

            Object obj = is.readObject();
            User user = (User) obj;
            System.out.println("user: " + user.getUsername() + "/" + user.getBirthday());

            user.setUsername(user.getUsername() + "_new");
            user.setBirthday(new Date());

            os.writeObject(user);
            os.flush();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
