package NewsPusherModule.entity;

import java.io.Serializable;
import java.net.Socket;

/**
 * <类详细说明:用于Mrp与客户端信息认证>
 *
 * @Author： Huanghai
 * @Version: 2016-12-28
 **/
public class HandShaker implements Serializable{
    private String companyId;
    private Socket clientSocket;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

}
