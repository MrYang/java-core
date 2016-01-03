package com.zz.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioBasicClient {

    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        //获得通道管理器
        try (SocketChannel channel = SocketChannel.open();
             Selector selector = Selector.open()) {

            channel.configureBlocking(false);
            //客户端连接服务器，需要调用channel.finishConnect();才能实际完成连接。
            channel.connect(new InetSocketAddress("localhost", PORT));
            //为该通道注册SelectionKey.OP_CONNECT事件
            channel.register(selector, SelectionKey.OP_CONNECT);

            while (true) {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    request(selector, selectionKey);
                }
            }
        }
    }

    private static void request(Selector selector, SelectionKey selectionKey) {
        if (selectionKey.isConnectable()) {
            try (SocketChannel channel = (SocketChannel) selectionKey.channel()) {
                //如果正在连接，则完成连接
                if (channel.isConnectionPending()) {
                    channel.finishConnect();
                    System.out.println("connect completely");
                }

                channel.configureBlocking(false);
                //向服务器发送消息
                channel.write(ByteBuffer.wrap("send message to server.".getBytes()));

                //连接成功后，注册接收服务器消息的事件
                channel.register(selector, SelectionKey.OP_READ);
                System.out.println("客户端连接成功");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (selectionKey.isReadable()) {
            try (SocketChannel channel = (SocketChannel) selectionKey.channel()) {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                channel.read(buffer);
                byte[] data = buffer.array();
                String message = new String(data);

                System.out.println("receive message from server:, size:" + buffer.position() + " msg: " + message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (selectionKey.isWritable()) {
            try (SocketChannel channel = (SocketChannel) selectionKey.channel()) {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                buffer.flip();
                channel.write(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
