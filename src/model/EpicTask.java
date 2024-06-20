package model;

import java.util.ArrayList;

public class EpicTask extends Task {
    public ArrayList<Integer> subTasksOfEpic = new ArrayList<>();

    public EpicTask(String taskTitle, String taskDescription) {
        super(taskTitle, taskDescription);
    }

    @Override
    public String toString() {
        return "[Эпик]\n" +
                "ID задачи: " + getTaskId() +
                ", Наименование задачи: " + getTaskTitle() + '\'' +
                ", Описание задачи: " + getTaskDescription() + '\'' +
                ", Статус задачи: " + getTaskStatus() +
                ", ID прикрепленных подзадач: " + subTasksOfEpic +
                '\n';

    }
}
