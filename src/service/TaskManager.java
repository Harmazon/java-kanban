package service;

import model.EpicTask;
import model.Subtask;
import model.Task;

import java.util.ArrayList;

public interface TaskManager {

    public void createTask(Task task);


    public void createEpic(EpicTask epicTask);


    public void createSubTask(Subtask subtask);


    public Task getTask(int id);


    public void printAllTasks();


    public ArrayList<Task> getAllSimpleTasks();


    public ArrayList<EpicTask> getAllEpics();


    public ArrayList<Subtask> getAllSubTasks();


    public ArrayList<Subtask> getSubTasksOfEpic(int id);


    public void removeTask(int id);

    public void removeAllTasks();

    public void updateTask(Task task);

    public void updateEpicTask(EpicTask epicTask);

    public void updateSubTask(Subtask subtask);

}
