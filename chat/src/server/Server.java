package server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class Server {

    private static DatagramSocket socket;
    private static boolean running;

    private static int ClientID;
    private static ArrayList<ClientInfo> clients = new ArrayList<>();



    static void start(int port) {
        try {
            socket = new DatagramSocket(port);
            running = true;
            listen();
            System.out.println("Server started on port" + port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stop() {
        running = false;
    }

    private static void send(String message, InetAddress address, int port) {
        try {
            message += "\\e ";
            byte[] data = message.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
            socket.send(packet);
            System.out.println("Send message to " + address.getHostAddress() + ":" + port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void listen() {
        Thread listenThread = new Thread(() -> {
            try {
                while (running) {
                    byte[] data = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(data, data.length);
                    socket.receive(packet);

                    String message = new String(data);
                    message = message.substring(0, message.indexOf("\\e"));

                    //Menage message
                    if (!isCommand(message, packet)) {
                        broadcast(message);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        listenThread.start();
    }

    private static void broadcast(String message) {
        for (ClientInfo info : clients) {
            send(message, info.getAddress(),info.getPort());
        }
    }


    /*
        Server command list:
         \\con:[name] -> connects a Client to the Server
         \\dis:[id] -> disconnects a Cliend from the Server

     */
    private static boolean isCommand(String message, DatagramPacket packet) {
        if (message.startsWith("\\con:")) {
            //Run connection code

            String name = message.substring(message.indexOf(":") + 1);
            clients.add(new ClientInfo(packet.getAddress(), packet.getPort(), ClientID++, name));
            broadcast("User " + name + " connected");

            return true;
        }

        return false;
    }

}
