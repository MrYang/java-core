package com.zz.io;

import java.io.*;
import java.util.Scanner;

public class IoSample {

    public static void readFile(File txtFile) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(txtFile)));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        br.close();
    }

    public static void readFile2(File txtFile) throws IOException {
        FileInputStream fis = new FileInputStream(txtFile);
        byte[] buf = new byte[1024];
        int hasRead;
        while ((hasRead = fis.read(buf)) > 0) {
            System.out.println(new String(buf, 0, hasRead));
        }
        fis.close();
    }

    public static void readFile3(File txtFile) throws IOException {
        FileReader fr = new FileReader(txtFile);
        char[] buf = new char[32];
        int hasRead;
        while ((hasRead = fr.read(buf)) > 0) {
            System.out.println(new String(buf, 0, hasRead));
        }
        fr.close();
    }

    public static void writeFile(File txtFile) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(txtFile)));
        bw.write("hello");
        bw.newLine();
        bw.write("world");
        bw.flush();
        bw.close();
    }

    public static void writeFile2(File txtFile) throws IOException {
        FileWriter fileWriter = new FileWriter(txtFile);
        fileWriter.write("hello，\n");
        fileWriter.write("world；\n");
        fileWriter.close();
    }

    public static void readFromStdout() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
            if ("exit".equals(line)) {
                System.exit(1);
            }
            System.out.println("输入内容为:" + line);
        }
    }

    public static void readFromStdout2() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println(sc.nextLine());
    }

    public static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        // IOUtils.copy(inputStream, outputStream);

        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
    }

    public static void copy(OutputStream outputStream) throws IOException {
        ByteArrayOutputStream baos = (ByteArrayOutputStream) outputStream;
        InputStream inputStream = new ByteArrayInputStream(baos.toByteArray());
    }
}
