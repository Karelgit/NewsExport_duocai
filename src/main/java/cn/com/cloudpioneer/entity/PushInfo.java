package cn.com.cloudpioneer.entity;

import java.io.Serializable;

/**
 * <类详细说明>
 *
 * @Author： HuangHai
 * @Version: 2016-12-30
 **/
public class PushInfo implements Serializable{
    private String userName;
    private String password;
    private String initEditor;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInitEditor() {
        return initEditor;
    }

    public void setInitEditor(String initEditor) {
        this.initEditor = initEditor;
    }
}
