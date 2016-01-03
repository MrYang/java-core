package com.zz.socket.basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BlockServer {

    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
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
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream())) {

            while (true) {
                String msg = in.readLine();
                System.out.println(msg);
                out.println("Server received " + msg);
                out.flush();
                if (msg.equals("bye")) {
                    break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
