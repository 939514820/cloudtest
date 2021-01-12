package com.example.gateway;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    public static void main(String[] args) {

        readFileByLines("/Users/bjhl/Documents/人群包01.txt", "/Users/bjhl/Documents/人群包导出01.txt");
        readFileByLines("/Users/bjhl/Documents/人群包03.txt", "/Users/bjhl/Documents/人群包导出03.txt");
        readFileByLines("/Users/bjhl/Documents/人群包02.txt", "/Users/bjhl/Documents/人群包导出02.txt");

//        System.out.println(stringToMD5("18220559940"));
    }

    public static void readFileByLines(String fileName, String targetName) {
        File file = new File(fileName);
        File result = new File(targetName);
        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(result));
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 0;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                //System.out.println(tempString);
                writer.write(MD5Utils.stringToMD5(tempString.trim())+"\n");
                line++;
            }
            System.out.println("总行数" + line);
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    public static String stringToMD5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有这个md5算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

}
