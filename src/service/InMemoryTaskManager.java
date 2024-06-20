package service;

import model.EpicTask;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.HashMap;
import java.util.ArrayList;

public class InMemoryTaskManager implements TaskManager {
    private int newId = 1;
    private int doneTrigger = 0;
    public HashMap<Integer, Task> tasksHashMap = new HashMap<>();
    public HashMap<Integer, EpicTask> epicTasksHashMap = new HashMap<>();
    public HashMap<Integer, Subtask> subTasksHashMap = new HashMap<>();
    InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
    public ArrayList<Task> historyArrList2 = new ArrayList<>();


    @Override
    public void createTask(Task task) {                                      // создание простой задачи
        task.setTaskId(newId);
        newId++;
        tasksHashMap.put(task.getTaskId(), task);
    }

    @Override
    public void createEpic(EpicTask epicTask) {                              // создание эпика
        epicTask.setTaskId(newId);
        newId++;
        epicTasksHashMap.put(epicTask.getTaskId(), epicTask);
    }

    @Override
    public void createSubTask(Subtask subtask) {                            // создание подзадачи
        subtask.setTaskId(newId);
        subTasksHashMap.put(subtask.getTaskId(), subtask);
        if (epicTasksHashMap.containsKey(subtask.epicId)) {
            epicTasksHashMap.get(subtask.epicId).subTasksOfEpic.add(subtask.getTaskId());
            newId++;
        } else {
            subTasksHashMap.remove(subtask.getTaskId());
        }
    }

    @Override
    public Task getTask(int id) {                         // вывести в консоль задачу, эпик или подзадачу
        final int MAX_HISTORY_LIST = 10;
        if (tasksHashMap.containsKey(id)) {
            if (!(inMemoryHistoryManager.historyArrList.size() >= MAX_HISTORY_LIST)) {
                inMemoryHistoryManager.historyArrList.add(tasksHashMap.get(id));
            } else {
                inMemoryHistoryManager.historyArrList.remove(0);
                inMemoryHistoryManager.historyArrList.add(tasksHashMap.get(id));
            }
            return tasksHashMap.get(id);                        // return задача
        } else if (epicTasksHashMap.containsKey(id)) {
            if (!(inMemoryHistoryManager.historyArrList.size() >= MAX_HISTORY_LIST)) {
                inMemoryHistoryManager.historyArrList.add(epicTasksHashMap.get(id));
            } else {
                inMemoryHistoryManager.historyArrList.remove(0);
                inMemoryHistoryManager.historyArrList.add(epicTasksHashMap.get(id));
            }
            return epicTasksHashMap.get(id);                    // return эпик
        } else if (subTasksHashMap.containsKey(id)) {
            if (!(inMemoryHistoryManager.historyArrList.size() >= MAX_HISTORY_LIST)) {
                inMemoryHistoryManager.historyArrList.add(subTasksHashMap.get(id));
            } else {
                inMemoryHistoryManager.historyArrList.remove(0);
                inMemoryHistoryManager.historyArrList.add(subTasksHashMap.get(id));
            }
            return subTasksHashMap.get(id);                     // return подзадача
        } else {
            System.out.println("Такого id нет");
            return null;
        }
    }

    /*private void setIdInHistoryArr(int id) {
        //final int MAX_HISTORY_LIST = 10;
        if (!(inMemoryHistoryManager.historyArrList.size() >= MAX_HISTORY_LIST)) {
            inMemoryHistoryManager.historyArrList.add(id);
        } else {
            inMemoryHistoryManager.historyArrList.remove(0);
            inMemoryHistoryManager.historyArrList.add(id);
        }
    } */

    @Override
    public void printAllTasks() {
        System.out.println("Простые задачи:");
        System.out.println(getAllSimpleTasks());
        System.out.println("Эпики:");
        System.out.println(getAllEpics());
        System.out.println("Подзадачи:");
        System.out.println(getAllSubTasks());
    }

    @Override
    public ArrayList<Task> getAllSimpleTasks() {
        return new ArrayList<>(tasksHashMap.values());
    }

    @Override
    public ArrayList<EpicTask> getAllEpics() {
        return new ArrayList<>(epicTasksHashMap.values());
    }

    @Override
    public ArrayList<Subtask> getAllSubTasks() {
        return new ArrayList<>(subTasksHashMap.values());
    }

    @Override
    public ArrayList<Subtask> getSubTasksOfEpic(int id) {
        ArrayList<Subtask> subTasksArr = new ArrayList<>();
        for (int key : epicTasksHashMap.get(id).subTasksOfEpic) {
            subTasksArr.add(subTasksHashMap.get(key));
        }
        return subTasksArr;
    }

    @Override
    public void removeTask(int id) {                                    // удалить определенную задачу или эпик
        if (tasksHashMap.containsKey(id)) {
            tasksHashMap.remove(id);                                    // удаляется простая задача по id
        } else if (epicTasksHashMap.containsKey(id)) {
            for (int key : epicTasksHashMap.get(id).subTasksOfEpic) {   // удаляются подзадачи эпика
                subTasksHashMap.remove(key);
            }
            epicTasksHashMap.get(id).subTasksOfEpic.clear();            // удаляются подзадачи эпика из листа
            epicTasksHashMap.remove(id);                                // удаляется эпик по id
        } else if (subTasksHashMap.containsKey(id)) {
            int epicID = subTasksHashMap.get(id).epicId;
            subTasksHashMap.remove(id);
            ArrayList<Integer> idsArrayList = new ArrayList<>(epicTasksHashMap.get(epicID).subTasksOfEpic);
            ArrayList<Integer> arrForCopy = new ArrayList<>();
            epicTasksHashMap.get(epicID).subTasksOfEpic.clear();
            for (int key : idsArrayList) {
                if (!(key == id)) {
                    arrForCopy.add(key);
                }
            }
            epicTasksHashMap.get(epicID).subTasksOfEpic = new ArrayList<>(arrForCopy);
            arrForCopy.clear();
            idsArrayList.clear();
            if (epicTasksHashMap.get(epicID).subTasksOfEpic.isEmpty()) {
                epicTasksHashMap.get(epicID).setTaskStatus(Status.NEW);
            }
        } else {
            System.out.println("Такого id нет");
        }
    }

    @Override
    public void removeAllTasks() {                                      // удалить все задачи и эпики
        tasksHashMap.clear();
        epicTasksHashMap.clear();
        subTasksHashMap.clear();
    }

    @Override
    public void updateTask(Task task) {
        tasksHashMap.put(task.getTaskId(), task);
    }

    @Override
    public void updateEpicTask(EpicTask epicTask) {
        epicTasksHashMap.put(epicTask.getTaskId(), epicTask);
    }

    @Override
    public void updateSubTask(Subtask subtask) {
        subTasksHashMap.put(subtask.getTaskId(), subtask);
        checkAndChangeStatus(subtask);
    }

    protected void checkAndChangeStatus(Subtask subtask) {
        if (subtask.getTaskStatus().equals(Status.IN_PROGRESS)) {
            epicTasksHashMap.get(subtask.epicId).setTaskStatus(Status.IN_PROGRESS);
        } else if (subtask.getTaskStatus().equals(Status.DONE)) {
            epicTasksHashMap.get(subtask.epicId).setTaskStatus(Status.IN_PROGRESS);
            doneTrigger++;
        }
        if (doneTrigger == epicTasksHashMap.get(subtask.epicId).subTasksOfEpic.size()) {
            epicTasksHashMap.get(subtask.epicId).setTaskStatus(Status.DONE);
            doneTrigger = 0;
        }
    }
}
