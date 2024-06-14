package model;

import java.util.ArrayList;

public class EpicTask extends Task {
    public EpicTask(String taskTitle, String taskDescription) {
        super(taskTitle, taskDescription);
    }

    public ArrayList<Integer> subTasksOfEpic = new ArrayList<>();

    @Override
    public String toString() {
        return "EpicTask{" +
                "taskId=" + getTaskId() +
                ", taskTitle='" + getTaskTitle() + '\'' +
                ", taskDescription='" + getTaskDescription() + '\'' +
                ", taskStatus=" + getTaskStatus() +
                ", subTasksOfEpic=" + subTasksOfEpic +
                '}';

    }
}
