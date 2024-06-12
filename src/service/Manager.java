package service;

import java.util.HashMap;
import java.util.ArrayList;

public class Manager {
    private int newId = 1;
    private int doneTrigger = 0;
    HashMap<Integer, model.Task> tasksHashMap = new HashMap<>();
    HashMap<Integer, model.Task> epicTasksHashMap = new HashMap<>();
    HashMap<Integer, model.Task> subTasksHashMap = new HashMap<>();
    HashMap<Integer, ArrayList<Integer>> idDataHashMap = new HashMap<>();

    public void createTask(model.Task task) {                                      // создание простой задачи
        task.setTaskId(newId);
        newId++;
        tasksHashMap.put(task.getTaskId(), task);
    }

    public void createEpic(model.EpicTask epicTask) {                              // *создание эпика
        epicTask.setTaskId(newId);
        newId++;
        epicTasksHashMap.put(epicTask.getTaskId(), epicTask);
    }

    public void createSubTask(int id, model.Subtask subtask) {          // создание подзадачи и помещение ее в эпик
        if (!idDataHashMap.containsKey(id)) {
            ArrayList<Integer> subTasksIds = new ArrayList<>();
            subtask.setTaskId(newId);
            newId++;
            subTasksIds.add(subtask.getTaskId());
            idDataHashMap.put(id, subTasksIds);
            subTasksHashMap.put(subtask.getTaskId(), subtask);
            if (epicTasksHashMap.containsKey(id)) {
                // *при добавлении подзадачи, статус эпика меняется на IN_PROGRESS
                getTask(id).setTaskStatus(model.Status.IN_PROGRESS);
            }
        } else {
            ArrayList<Integer> subTasksIds = idDataHashMap.get(id);
            subtask.setTaskId(newId);
            newId++;
            subTasksIds.add(subtask.getTaskId());
            idDataHashMap.put(id, subTasksIds);
            subTasksHashMap.put(subtask.getTaskId(), subtask);
        }
    }

    public model.Task getTask(int id) {                         // вывести в консоль задачу, эпик или подзадачу
        if (tasksHashMap.containsKey(id)) {
            return tasksHashMap.get(id);                        // return задача
        } else if (epicTasksHashMap.containsKey(id)) {
            return epicTasksHashMap.get(id);                    // return эпик
        } else if (subTasksHashMap.containsKey(id)) {
            return subTasksHashMap.get(id);                     // return подзадача
        } else {
            System.out.println("Такого id нет");
            return null;
        }
    }

    public void printAllTasks() {                               // печать в консоль всех задач (для проверки программы)
        System.out.println("Простые задачи:");
        System.out.println(getAllSimpleTasks());
        System.out.println("Эпики:");
        System.out.println(getAllEpics());
        System.out.println("Подзадачи:");
        System.out.println(getAllSubTasks());
    }

    public ArrayList<model.Task> getAllSimpleTasks() {                         // *исправил методы вывода задач
        return new ArrayList<>(tasksHashMap.values());
    }

    public ArrayList<model.Task> getAllEpics() {
        return new ArrayList<>(epicTasksHashMap.values());
    }

    public ArrayList<model.Task> getAllSubTasks() {                            // *добавил вывод всех существующих подзадач
        return new ArrayList<>(subTasksHashMap.values());
    }

    public void printSubTasksOfEpic(int id) {   // печать в консоль подзадач конкретного эпика (для проверки программы)
        if (epicTasksHashMap.containsKey(id) && idDataHashMap.containsKey(id)) {
            for (Integer idNum : idDataHashMap.get(id)) {
                int keyForSubtaskHashMap = idNum;
                System.out.println(subTasksHashMap.get(keyForSubtaskHashMap));
            }
        } else {
            System.out.println("Такого id нет");
        }
    }

    public void removeTask(int id) {                                    // удалить определенную задачу или эпик
        if (tasksHashMap.containsKey(id)) {
            tasksHashMap.remove(id);                                    // удаляется простая задача по id
        } else if (epicTasksHashMap.containsKey(id)) {
            epicTasksHashMap.remove(id);                                // удаляется эпик по id
            removeSubTasksOfEpic(id);                                   // удаляются его подзадачи
        } else if (subTasksHashMap.containsKey(id)) {
            subTasksHashMap.remove(id);                                 // удаляется подзадача по id
        } else {
            System.out.println("Такого id нет");
        }
    }

    public void removeAllTasks() {                                        // удалить все задачи и эпики
        tasksHashMap.clear();
        epicTasksHashMap.clear();
        subTasksHashMap.clear();
    }

    protected void removeSubTasksOfEpic(int id) {                         // удалить все подзадачи определенного эпика
        ArrayList<Integer> arrayWithId = idDataHashMap.get(id);
        for (int i = 0; i < arrayWithId.size(); i++) {
            if (subTasksHashMap.containsKey(arrayWithId.get(i))) {
                subTasksHashMap.remove(arrayWithId.get(i));
            }
        }
    }

    public void updateTask(model.Task task) {                               // *изменил методы update
        tasksHashMap.put(task.getTaskId(), task);
    }

    public void updateEpicTask(model.EpicTask epicTask) {
        epicTasksHashMap.put(epicTask.getTaskId(), epicTask);
    }

    public void updateSubTask(model.Subtask subtask) {
        subTasksHashMap.put(subtask.getTaskId(), subtask);

    }

    public model.Status changeAndCheckEpicStatus(int id) {
        ArrayList<Integer> arrayWithId = idDataHashMap.get(id);
        for (int i = 0; i < arrayWithId.size(); i++) {
            if (subTasksHashMap.containsKey(arrayWithId.get(i))) {
                int bufer = arrayWithId.get(i);
                if (getTask(bufer).getTaskStatus().equals(model.Status.IN_PROGRESS)) {
                    getTask(id).setTaskStatus(model.Status.IN_PROGRESS);
                } else if (getTask(bufer).getTaskStatus().equals(model.Status.DONE)) {
                    getTask(id).setTaskStatus(model.Status.IN_PROGRESS);
                    doneTrigger++;
                }
                if (doneTrigger == arrayWithId.size()) {
                    getTask(id).setTaskStatus(model.Status.DONE);
                    doneTrigger = 0;
                }
            }
        }
        return getTask(id).getTaskStatus();
    }
}
