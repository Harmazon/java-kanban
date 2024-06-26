package model;

import java.util.ArrayList;

public class EpicTask extends Task {
    private ArrayList<Integer> subTasksOfEpic = new ArrayList<>();

    public EpicTask(String taskTitle, String taskDescription) {
        super(taskTitle, taskDescription);
    }

    public ArrayList<Integer> getSubTasksOfEpic() {
        return subTasksOfEpic;
    }

    public void setSubTasksOfEpic(ArrayList<Integer> subTasksOfEpic) {
        this.subTasksOfEpic = subTasksOfEpic;
    }

    @Override
    public String toString() {
        return "[Эпик]\n" +
                "ID задачи: " + getTaskId() +
                ", Наименование задачи: " + getTaskTitle() + '\'' +
                ", Описание задачи: " + getTaskDescription() + '\'' +
                ", Статус задачи: " + getTaskStatus() +
                ", ID прикрепленных подзадач: " + getSubTasksOfEpic() +
                '\n';

    }
}
