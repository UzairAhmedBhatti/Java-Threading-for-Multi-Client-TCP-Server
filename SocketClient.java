package com.company;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class SocketClient {

    private static String SERVER_IP ;
    private static final int SERVER_PORT = 8888;

    private static String CLIENT_IP ;
    private static int CLIENT_PORT;

    public static void main(String[] args) throws IOException {

        try{

            InetAddress ip = InetAddress.getLocalHost();
            SERVER_IP = ip.getHostAddress();

            Socket socket = new Socket(SERVER_IP, SERVER_PORT);

            CLIENT_IP = socket.getRemoteSocketAddress().toString().split(":")[0];
            CLIENT_IP = CLIENT_IP.substring(1);
            CLIENT_PORT = Integer.parseInt(socket.getRemoteSocketAddress().toString().split(":")[1]);

            System.out.println("Client " + CLIENT_IP + " is active.");

            DataInputStream inStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String clientMessage = "", serverMessage = "";
            int option = -1;

            while(option != 3) {

                System.out.println("Enter 1 if you want to read data from server and 2 if you want to write data to the server and 3 to exit!");
                try {
                    option = Integer.parseInt(br.readLine());
                }
                catch (NumberFormatException ex)
                {
                    System.out.println("Please enter a valid number!");
                    option = Integer.parseInt(br.readLine());
                }

                if (option == 1) {
                    System.out.println("Enter Client Information:");
                    clientMessage = br.readLine();
                    while(clientMessage.isEmpty()) {
                        System.out.println("Enter Client Information:");
                        clientMessage = br.readLine();
                    }
                    outStream.writeUTF(clientMessage);
                    outStream.flush();
                } else if (option == 2) {
                    clientMessage = "";
                    outStream.writeUTF(clientMessage);
                    outStream.flush();
                    serverMessage = inStream.readUTF();
                    System.out.println("Information from file: " + serverMessage);
                }
            }

            outStream.close();
            outStream.close();
            socket.close();

        }catch(Exception e){
            System.out.println(e);
        }

    }
}
