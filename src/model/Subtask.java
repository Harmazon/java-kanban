package model;

public class Subtask extends Task {
    public int epicId;

    public Subtask(String taskTitle, String taskDescription, int epicId) {
        super(taskTitle, taskDescription);
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "[Подзадача]\n" +
                "ID подзадачи: " + getTaskId() +
                ", Наименование подзадачи: " + getTaskTitle() + '\'' +
                ", Описание подзадачи: " + getTaskDescription() + '\'' +
                ", Статус подзадачи: " + getTaskStatus() +
                ", ID папы-эпика " + epicId +
                '\n';
    }
}
