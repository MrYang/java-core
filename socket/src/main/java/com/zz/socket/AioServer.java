package com.zz.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AioServer {

    private static final int PORT = 8000;

    public static void main(String[] args) throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(20);
        // 异步通道管理器
        AsynchronousChannelGroup asyncChannelGroup = AsynchronousChannelGroup.withThreadPool(executor);
        // 创建 用在服务端的异步Socket.以下简称服务器socket。
        // 异步通道管理器
        AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open(asyncChannelGroup)
                .bind(new InetSocketAddress(PORT));

        Runnable r = () -> handleRequest(server);
        executor.submit(r);
    }

    private static void handleRequest(AsynchronousServerSocketChannel server) {
        server.accept(
                server,
                new CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel>() {
                    String str = "<html><head><title>test</title></head><body><p>this is a socket server test</p></body></html>";
                    String CRLF = "\r\n";
                    // 响应头的参数
                    String serverLine = "Server:a simple java WebServer";
                    String statusLine = "HTTP/1.1 200 OK" + CRLF;
                    String contentTypeLine = "Content-type:text/html"
                            + CRLF;
                    String contentLengthLine = "Content-Length:" + str.length()
                            + CRLF;

                    @Override
                    public void completed(AsynchronousSocketChannel result,
                                          AsynchronousServerSocketChannel attachment) {

                        writeChannel(result, statusLine + serverLine
                                + contentTypeLine + contentLengthLine
                                + CRLF + str);
                        try {
                            result.shutdownOutput();
                            result.shutdownInput();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                result.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        attachment.accept(attachment, this);
                    }

                    @Override
                    public void failed(Throwable exc,
                                       AsynchronousServerSocketChannel attachment) {
                    }

                    public void writeChannel(
                            AsynchronousSocketChannel channel, String s) {
                        Future<Integer> future = channel.write(ByteBuffer
                                .wrap(s.getBytes()));
                        try {
                            future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
