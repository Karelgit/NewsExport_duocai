package NewsPusherModule.entity;

/**
 *登陆多彩贵州网需要的信息
 */
public class DuocaiInfo {
    private String userName;
    private String password;
    private String initEditor;
    private String api_token;
    private String seed;

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

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

}
