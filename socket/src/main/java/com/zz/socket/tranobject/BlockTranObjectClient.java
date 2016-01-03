package com.zz.socket.tranobject;

import com.zz.common.entity.User;

import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

public class BlockTranObjectClient {

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 100; i++) {
            try (Socket socket = new Socket("localhost", 8080);
                 ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()))) {

                User user = new User("user_" + i, new Date());
                os.writeObject(user);
                os.flush();

                Object obj = is.readObject();
                if (obj != null) {
                    user = (User) obj;
                    System.out.println("user: " + user.getUsername() + "/" + user.getBirthday());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
