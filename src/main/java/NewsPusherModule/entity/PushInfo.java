package NewsPusherModule.entity;

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
    private String articlesJSON;
    private String customerInfoJSON;

    public String getCustomerInfoJSON() {
        return customerInfoJSON;
    }

    public void setCustomerInfoJSON(String customerInfoJSON) {
        this.customerInfoJSON = customerInfoJSON;
    }

    public String getArticlesJSON() {
        return articlesJSON;
    }

    public void setArticlesJSON(String articlesJSON) {
        this.articlesJSON = articlesJSON;
    }

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
