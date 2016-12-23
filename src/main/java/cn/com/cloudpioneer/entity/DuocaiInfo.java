package cn.com.cloudpioneer.entity;

/**
 *登陆多彩贵州网需要的信息
 */
public class DuocaiInfo {
    private String userName;
    private String password;
    private String initEditor;
    private String channelId;
    private String templateId;

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

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }
}
