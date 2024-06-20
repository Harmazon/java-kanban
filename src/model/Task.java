package model;

public class Task {
    private int taskId;
    private String taskTitle;
    private String taskDescription;
    private Status taskStatus;

    public Task(String taskTitle, String taskDescription) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskStatus = Status.NEW;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void setTaskStatus(Status taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Status getTaskStatus() {
        return taskStatus;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    @Override
    public String toString() {
        return "[Задача]\n" +
                "ID задачи: " + getTaskId() +
                ", Наименование задачи: " + getTaskTitle() + '\'' +
                ", Описание задачи: " + getTaskDescription() + '\'' +
                ", Статус задачи: " + getTaskStatus() +
                '\n';
    }
}
