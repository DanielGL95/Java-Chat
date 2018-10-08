package client;

import server.ClientInfo;

import java.net.*;

public class Client {
    private static DatagramSocket socket;
    private InetAddress address;
    private int port;
    private static boolean running;
    private String name;

    public Client(String name, String address, int port) {
        this.name = name;
        try {
            this.address = InetAddress.getByName(address);
            this.port = port;
            socket = new DatagramSocket();
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }

        running = true;
        listen();

        send("\\con:" + name);
    }

    public void send(String message) {
        try {
            if (!message.startsWith("\\")){
                message = name + " : " + message;
            }

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
                        //Print message
                        System.out.println(message);
                        ClientWindow.printToConsole(message);


                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        listenThread.start();
    }

    private static boolean isCommand(String message, DatagramPacket packet) {
        if (message.startsWith("\\con:")) {
            //Run connection code

            return true;
        }

        return false;
    }

}
