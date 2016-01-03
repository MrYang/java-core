package com.zz.socket.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpServer {

    private static final int PORT = 5020;

    public static void main(String[] args) throws Exception {
        byte[] buffer = new byte[8192];
        try (DatagramSocket server = new DatagramSocket(PORT)) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            while (true) {
                server.receive(packet);

                String s = new String(packet.getData(), packet.getOffset(), packet.getLength(), "UTF-8");
                System.out.println(packet.getAddress() + " at port:"
                        + packet.getPort() + " says:\n" + s);
                //设置以后需要接受的长度
                packet.setLength(buffer.length);
            }
        }
    }
}
