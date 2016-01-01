package com.zz.socket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BlockServer {

    public static void main(String[] args) throws IOException {
        Executor pool = Executors.newCachedThreadPool();
        ServerSocket socket = new ServerSocket(8080);

        while (true) {
            final Socket connection = socket.accept();
            Runnable r = () -> handleRequest(connection);
            pool.execute(r);
        }
    }

    private static void handleRequest(Socket connection) {
    }
}
