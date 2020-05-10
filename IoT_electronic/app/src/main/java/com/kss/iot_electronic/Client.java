package com.kss.iot_electronic;

public class Client {

    private String clientId;
    private String ip;
    private String port;

    public Client(String clientId, String ip, String port) {
        this.clientId = clientId;
        this.ip = ip;
        this.port = port;
    }

    public String getClientId() {
        return clientId;
    }

    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
