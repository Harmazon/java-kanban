package service;

import model.EpicTask;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    Task createTask(Task task);

    EpicTask createEpic(EpicTask epicTask);

    Subtask createSubTask(Subtask subtask);

    Task getTask(int id);

    void printAllTasks();

    ArrayList<Task> getAllSimpleTasks();

    ArrayList<EpicTask> getAllEpics();

    ArrayList<Subtask> getAllSubTasks();

    ArrayList<Subtask> getSubTasksOfEpic(int id);

    void removeTask(int id);

    void removeEpic(int id);

    void removeSubTask(int id);

    void removeAllTasks();

    void removeAllEpics();

    void removeAllSubTasks();

    void updateTask(Task task);

    void updateEpicTask(EpicTask epicTask);

    void updateSubTask(Subtask subtask);

    List<Task> getHistory();
}
