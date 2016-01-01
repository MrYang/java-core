package com.zz.feature.java7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Syntax {

    public void syntaxSugar(){
        int one_million = 1_000_000;
        List<String> list = new ArrayList<>();
        Set<String> set = new HashSet<>();
        Map<String, String> map = new HashMap<>();
    }

    public void switchString(String str){
        switch (str){
            case "foo":
                System.out.println("foo");
                break;
            case "bar":
                System.out.println("bar");
                break;
            default:
                System.out.println("default");
        }
    }

    public void tryWithResource(String path){
        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            String str;
            while ((str = br.readLine()) != null){
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void catchMulitException(){
        try {
            throwIOException();
            throwInterruptedException();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void throwIOException() throws IOException{

    }

    private void throwInterruptedException() throws InterruptedException{

    }
}
