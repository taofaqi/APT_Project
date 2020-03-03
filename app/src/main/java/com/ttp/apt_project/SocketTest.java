package com.ttp.apt_project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.net.SocketFactory;

/**
 * @author faqi.tao
 * @time 2020/2/8
 */
public class SocketTest {
    public static void main(String[] args) {
        socketHttp();
    }

    private static void socketHttp() {
        try {
            Socket socket = SocketFactory.getDefault().createSocket("www.baidu.com", 443);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            bw.write("GET / HTTP/1.1\r\n");
            bw.write("Host: www.baidu.com");
            bw.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));


            while (true) {
                String readLine = null;
                if ((readLine = br.readLine()) != null) {
                    System.out.println("响应数据：" + readLine);
                } else {
                    break;
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
