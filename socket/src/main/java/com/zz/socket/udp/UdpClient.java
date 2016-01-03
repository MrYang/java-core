package com.zz.socket.udp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpClient {

    private static final int PORT = 5020;

    public static void main(String[] args) throws Exception {
        while (true) {
            try (DatagramSocket client = new DatagramSocket();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                String msg = reader.readLine();
                if (msg.equals("bye")) {
                    break;
                }
                byte[] data = msg.getBytes("UTF-8");

                //数据报
                DatagramPacket packet = new DatagramPacket(data, data.length,
                        InetAddress.getByName("localhost"), PORT);
                client.send(packet);
            }
        }
    }
}
