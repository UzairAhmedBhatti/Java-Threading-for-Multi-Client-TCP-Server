package com.company;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ServerClientThread extends Thread{

    Socket serverClient;
    int clientNo;

    private static String SERVER_IP ;
    private static final int SERVER_PORT = 8888;
    private static String CLIENT_IP ;
    private static int CLIENT_PORT;

    ServerClientThread(Socket inSocket, int counter){
        serverClient = inSocket;
        clientNo = counter;

    }
    public void run(){
        try{

            InetAddress ip = InetAddress.getLocalHost();
            SERVER_IP = ip.getHostAddress();

            DataInputStream inStream = new DataInputStream(serverClient.getInputStream());
            DataOutputStream outStream = new DataOutputStream(serverClient.getOutputStream());
            String clientMessage = "", serverMessage = "";

            CLIENT_IP = serverClient.getRemoteSocketAddress().toString().split(":")[0];
            CLIENT_IP = CLIENT_IP.substring(1);
            CLIENT_PORT = Integer.parseInt(serverClient.getRemoteSocketAddress().toString().split(":")[1]);



            while(!clientMessage.equals("bye")) {
            clientMessage = inStream.readUTF();
            String string = "";
            if(!clientMessage.equals(""))
            {
                System.out.println("\nWelcome to the server " + SERVER_IP +  " : " + SERVER_PORT);

                try {
                    //File file = new File(CLIENT_IP + ".txt");
                    FileInputStream fileInputStream = new FileInputStream(CLIENT_IP + ".txt");
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

                    int ch = 0;
                    while ((ch = bufferedInputStream.read()) > -1) {
                        string += (char) ch;
                    }

                } catch (FileNotFoundException e) {
                    //e.printStackTrace();
                } catch (IOException e) {
                    //e.printStackTrace();
                }

                string += clientMessage;

                BufferedOutputStream out = null;
                try {
                    out = new BufferedOutputStream(new FileOutputStream(CLIENT_IP + ".txt"));
                    out.write(string.getBytes());
                    out.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                System.out.println("Information saved for client " + CLIENT_IP + "\n");
            }
            else {
                    File file = new File(CLIENT_IP + ".txt");
                    if (file.exists() && file.isFile()) {
                        try {
                            FileInputStream fileInputStream = new FileInputStream(CLIENT_IP + ".txt");
                            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

                            int ch = 0;
                            while ((ch = bufferedInputStream.read()) > -1) {
                                string += (char) ch;
                            }

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        serverMessage = string;
                        System.out.println("Information for client " + CLIENT_IP + " is " + string);

                    } else {
                        System.out.println("No Information found for client " + CLIENT_IP);
                    }

                    BufferedOutputStream out = null;
                    try {
                        out = new BufferedOutputStream(new FileOutputStream(CLIENT_IP + "_" + SERVER_IP + ".txt"));
                        out.write(serverMessage.getBytes());
                        out.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    outStream.writeUTF(serverMessage);
                    outStream.flush();
                }
            }
            inStream.close();
            outStream.close();
            serverClient.close();
        }catch(Exception ex){
            System.out.println(ex);
        }finally{
            System.out.println("Client " + CLIENT_IP + " exit!! ");
            SocketServer.counter--;
        }
    }
}
