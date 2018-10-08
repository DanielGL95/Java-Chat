package server;

import java.net.InetAddress;

public class ClientInfo {

    private InetAddress address;
    private int port;
    private int id;
    private String name;

    ClientInfo(InetAddress address, int port, int id, String name) {
        this.address = address;
        this.port = port;
        this.id = id;
        this.name = name;
    }

    InetAddress getAddress() {
        return address;
    }

    int getPort() {
        return port;
    }

}
