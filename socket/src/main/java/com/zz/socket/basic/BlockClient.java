package com.zz.socket.basic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BlockClient {

    private static final int PORT = 8080;

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream());
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            while (true) {
                String msg = reader.readLine();
                out.println(msg);
                out.flush();
                if (msg.equals("bye")) {
                    break;
                }
                System.out.println(in.readLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
