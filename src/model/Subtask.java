package model;

public class Subtask extends Task{
    public Subtask(String taskTitle, String taskDescription) {
        super(taskTitle, taskDescription);
    }
    public int epicId;

    @Override
    public String toString() {
        return "Subtask{" +
                "taskId=" + getTaskId() +
                ", taskTitle='" + getTaskTitle() + '\'' +
                ", taskDescription='" + getTaskDescription() + '\'' +
                ", taskStatus=" + getTaskStatus() +
                ", epicId=" + epicId +
                '}';
    }
}
