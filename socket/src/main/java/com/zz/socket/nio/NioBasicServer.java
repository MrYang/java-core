package com.zz.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioBasicServer {

    private static final int PORT = 8080;

    public static void main(String[] args) {
        try (Selector selector = Selector.open();
             ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
             ServerSocket serverSocket = serverSocketChannel.socket()) {
            serverSocketChannel.configureBlocking(false);

            serverSocket.setReuseAddress(true);
            serverSocket.bind(new InetSocketAddress(PORT));

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                selector.select();

                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    handleRequest(selector, selectionKey);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleRequest(Selector selector, SelectionKey selectionKey) {
        if (selectionKey.isAcceptable()) {
            try (ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                 // 获得客户端连接通道
                 SocketChannel channel = server.accept()) {

                channel.configureBlocking(false);
                // 向客户端发消息
                channel.write(ByteBuffer.wrap("send message to client".getBytes()));
                // 在与客户端连接成功后，为客户端通道注册SelectionKey.OP_READ, SelectionKey.OP_WRITE事件
                channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

                System.out.println("客户端请求连接事件");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (selectionKey.isReadable()) {
            try (SocketChannel client = (SocketChannel) selectionKey.channel()) {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                client.read(buffer);
                byte[] data = buffer.array();
                String message = new String(data);

                System.out.println("receive message from client, size:" + buffer.position() + " msg: " + message);

                selectionKey.interestOps(SelectionKey.OP_WRITE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (selectionKey.isWritable()) {
            try (SocketChannel client = (SocketChannel) selectionKey.channel()) {
                ByteBuffer send = ByteBuffer.allocate(1024);
                // 输出到通道
                client.write(send);
                selectionKey.interestOps(SelectionKey.OP_READ);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
