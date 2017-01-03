package cn.com.cloudpioneer.entity;

import java.io.Serializable;
import java.net.Socket;

/**
 * <类详细说明:用于Mrp与客户端信息认证>
 *
 * @Author： Huanghai
 * @Version: 2016-12-28
 **/
public class HandShaker implements Serializable{
    private String username;
    private String password;
    private Socket clientSocket;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

}
