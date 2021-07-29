package com.company;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    private static int SERVER_PORT = 8888;

    private static String SERVER_IP;
    public static String CLIENT_IP ;
    public static int CLIENT_PORT;

    public static int counter = 0;

    public static void main(String[] args) throws IOException {

        try{
            ServerSocket server = new ServerSocket(SERVER_PORT);

            InetAddress ip = InetAddress.getLocalHost();
            SERVER_IP = ip.getHostAddress();

            System.out.println("Waiting for clients on port " + SERVER_PORT);
            while(true){
                counter++;
                Socket serverClient = server.accept();  //server accept the client connection request
                CLIENT_IP = serverClient.getRemoteSocketAddress().toString().split(":")[0];
                CLIENT_IP = CLIENT_IP.substring(1);
                CLIENT_PORT = Integer.parseInt(serverClient.getRemoteSocketAddress().toString().split(":")[1]);

                System.out.println("Got connection from " + CLIENT_IP +  " : " + CLIENT_PORT);
                System.out.println("Active Connections = " + counter);

                ServerClientThread sct = new ServerClientThread(serverClient, counter); //send  the request to a separate thread
                sct.start();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
