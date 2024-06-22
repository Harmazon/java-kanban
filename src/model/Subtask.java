package model;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String taskTitle, String taskDescription, int epicId) {
        super(taskTitle, taskDescription);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "[Подзадача]\n" +
                "ID подзадачи: " + getTaskId() +
                ", Наименование подзадачи: " + getTaskTitle() + '\'' +
                ", Описание подзадачи: " + getTaskDescription() + '\'' +
                ", Статус подзадачи: " + getTaskStatus() +
                ", ID папы-эпика " + getEpicId() +
                '\n';
    }
}
