package cn.com.cloudpioneer.entity;

/**
 * Created by Tijun on 2016/9/28.
 */
public class TaskEntity {
    private int id;
    private String taskId;
    private int position;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
