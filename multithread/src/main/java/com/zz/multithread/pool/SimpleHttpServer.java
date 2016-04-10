package com.zz.multithread.pool;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleHttpServer {

    static ThreadPool<HttpRequestHandler> threadPool = new DefaultThreadPool<>(1);

    static String basePath;

    static ServerSocket serverSocket;

    static int port = 8080;

    public static void setPort(int port) {
        if (port > 0) {
            SimpleHttpServer.port = port;
        }
    }

    public static void setBasePath(String basePath) {
        if (basePath != null && new File(basePath).exists() && new File(basePath).isDirectory()) {
            SimpleHttpServer.basePath = basePath;
        }
    }

    public static void start() throws Exception {
        serverSocket = new ServerSocket(port);
        Socket socket;

        while ((socket = serverSocket.accept()) != null) {
            threadPool.execute(new HttpRequestHandler(socket));
        }

        serverSocket.close();
    }


    static class HttpRequestHandler implements Runnable {
        private Socket socket;

        public HttpRequestHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream())) {
                try {
                    String header = reader.readLine();
                    String filePath = basePath + header.split(" ")[1];
                    if (filePath.endsWith("jpg") || filePath.endsWith("ico")) {
                        try (InputStream in = new FileInputStream(filePath);
                             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                            int i;
                            while ((i = in.read()) != -1) {
                                baos.write(i);
                            }

                            byte[] array = baos.toByteArray();
                            out.println("HTTP/1.1 200 OK");
                            out.println("Server: Molly");
                            out.println("Content-Type: image/jpeg");
                            out.println("Content-length: " + array.length);
                            out.println("");
                            socket.getOutputStream().write(array, 0, array.length);
                        }
                    } else {
                        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
                            out.println("HTTP/1.1 200 OK");
                            out.println("Server: Molly");
                            out.println("Content-Type: text/html; charset=UTF-8");
                            out.println("");
                            String line;
                            while ((line = br.readLine()) != null) {
                                out.println(line);
                            }
                        }

                        out.flush();
                    }
                } catch (Exception e) {
                    out.println("HTTP/1.1 500");
                    out.println("");
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
