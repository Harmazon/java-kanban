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

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", taskTitle='" + taskTitle + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskStatus=" + taskStatus +
                '}';
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
}
