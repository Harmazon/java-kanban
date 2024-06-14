package service;

import model.EpicTask;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.HashMap;
import java.util.ArrayList;

public class Manager {
    private int newId = 1;
    private int doneTrigger = 0;
    private int keyOfIdDeposit = 0;
    HashMap<Integer, Task> tasksHashMap = new HashMap<>();
    HashMap<Integer, EpicTask> epicTasksHashMap = new HashMap<>();
    HashMap<Integer, Subtask> subTasksHashMap = new HashMap<>();
    HashMap<Integer, ArrayList<Integer>> idDataHashMap = new HashMap<>();

    public void createTask(Task task) {                                      // создание простой задачи
        task.setTaskId(newId);
        newId++;
        tasksHashMap.put(task.getTaskId(), task);
    }

    public void createEpic(EpicTask epicTask) {                              // создание эпика
        epicTask.setTaskId(newId);
        newId++;
        epicTasksHashMap.put(epicTask.getTaskId(), epicTask);
    }

    public void createSubTask(Subtask subtask) {                      // *создание подзадачи
        subtask.setTaskId(newId);
        newId++;
        subTasksHashMap.put(subtask.getTaskId(), subtask);
    }

    public void putSubTaskInEpic(int id, Subtask subtask) {          // помещение подзадачи в эпик
        if (!idDataHashMap.containsKey(id)) {
            ArrayList<Integer> subTasksIds = new ArrayList<>();
            createSubTask(subtask);
            subTasksIds.add(subtask.getTaskId());
            idDataHashMap.put(id, subTasksIds);
        } else {
            ArrayList<Integer> subTasksIds = idDataHashMap.get(id);
            createSubTask(subtask);
            subTasksIds.add(subtask.getTaskId());
            idDataHashMap.put(id, subTasksIds);
        }
    }

    public Task getTask(int id) {                         // вывести в консоль задачу, эпик или подзадачу
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

    public void printAllTasks() {
        System.out.println("Простые задачи:");
        System.out.println(getAllSimpleTasks());
        System.out.println("Эпики:");
        System.out.println(getAllEpics());
        System.out.println("Подзадачи:");
        System.out.println(getAllSubTasks());
    }

    public ArrayList<Task> getAllSimpleTasks() {
        return new ArrayList<>(tasksHashMap.values());
    }

    public ArrayList<EpicTask> getAllEpics() {
        return new ArrayList<>(epicTasksHashMap.values());
    }

    public ArrayList<Subtask> getAllSubTasks() {
        return new ArrayList<>(subTasksHashMap.values());
    }

    public ArrayList<Subtask> getSubTasksOfEpic(int id) {                     // *изменил метод
        if (epicTasksHashMap.containsKey(id) && idDataHashMap.containsKey(id)) {
            ArrayList<Subtask> subTasksOfEpic = new ArrayList<>();
            for (Integer idNum : idDataHashMap.get(id)) {
                subTasksOfEpic.add(subTasksHashMap.get(idNum));
            }
            return subTasksOfEpic;
        } else {
            return null;
        }
    }

    public void removeTask(int id) {                                    // удалить определенную задачу или эпик
        if (tasksHashMap.containsKey(id)) {
            tasksHashMap.remove(id);                                    // удаляется простая задача по id
        } else if (epicTasksHashMap.containsKey(id)) {
            epicTasksHashMap.remove(id);                                // удаляется эпик по id
            removeSubTasksOfEpic(id);                                   // удаляются его подзадачи
        } else if (subTasksHashMap.containsKey(id)) {
            // *получаем нужный ключ, где хранится искомый ID подзадачи
            for (int key : idDataHashMap.keySet()) {
                for (int ids : idDataHashMap.get(key)) {
                    if (ids == id) {
                        keyOfIdDeposit = key;
                    }
                }
            }
            subTasksHashMap.remove(id);                                     // удаляется подзадача по id
            // *метод установки статуса NEW для эпика, после удаления всех его подзадач
            checkAndChangeEpicStatusAfterDelSubTask(keyOfIdDeposit, id);
        } else {
            System.out.println("Такого id нет");
        }
    }

    public void removeAllTasks() {                                      // удалить все задачи и эпики
        tasksHashMap.clear();
        epicTasksHashMap.clear();
        subTasksHashMap.clear();
    }

    protected void removeSubTasksOfEpic(int id) {                       // *исправлен метод удаления подзадач
        ArrayList<Integer> arrayWithId = idDataHashMap.get(id);
        for (int i = 0; i < arrayWithId.size(); i++) {
            if (subTasksHashMap.containsKey(arrayWithId.get(i))) {
                subTasksHashMap.remove(arrayWithId.get(i));
            }
        }
        idDataHashMap.remove(id);                       // *теперь удаляется соответствующий массив id подзадач
    }

    public void updateTask(Task task) {                               // *изменил методы update
        tasksHashMap.put(task.getTaskId(), task);
    }

    public void updateEpicTask(EpicTask epicTask) {
        epicTasksHashMap.put(epicTask.getTaskId(), epicTask);
    }

    public void updateSubTask(Subtask subtask) {
        subTasksHashMap.put(subtask.getTaskId(), subtask);
        checkAndChangeStatus(subtask);
    }

    // *переписал код, теперь проверка и изменения статуса эпика проходит корректно
    protected void checkAndChangeStatus(Subtask subtask) {
        int buferID;
        int keyOfIdDeposit = 0;
        if (subtask.getTaskStatus().equals(Status.IN_PROGRESS)) {
            buferID = subtask.getTaskId();
            for (int key : idDataHashMap.keySet()) {
                for (int id : idDataHashMap.get(key)) {
                    if (id == buferID) {
                        keyOfIdDeposit = key;
                    }
                }
            }
            getTask(keyOfIdDeposit).setTaskStatus(Status.IN_PROGRESS);
        } else if (subtask.getTaskStatus().equals(Status.DONE)) {
            buferID = subtask.getTaskId();
            for (int key : idDataHashMap.keySet()) {
                for (int id : idDataHashMap.get(key)) {
                    if (id == buferID) {
                        keyOfIdDeposit = key;
                    }
                }
            }
            getTask(keyOfIdDeposit).setTaskStatus(Status.IN_PROGRESS);
            doneTrigger++;
        }
        if (doneTrigger == idDataHashMap.get(keyOfIdDeposit).size()) {
            getTask(keyOfIdDeposit).setTaskStatus(Status.DONE);
            doneTrigger = 0;
        }
    }

    protected void checkAndChangeEpicStatusAfterDelSubTask(int keyOfIdDeposit, int idSubtask) {
        ArrayList<Integer> idsArrayList = new ArrayList<>(idDataHashMap.get(keyOfIdDeposit));
        ArrayList<Integer> arrayListForCopy = new ArrayList<>();
        idDataHashMap.remove(keyOfIdDeposit);
        for (Integer ids : idsArrayList) {
            if (!(ids == idSubtask)) {
                arrayListForCopy.add(ids);
            }
        }
        idDataHashMap.put(keyOfIdDeposit, arrayListForCopy);
        if (idDataHashMap.get(keyOfIdDeposit).isEmpty()) {
            getTask(keyOfIdDeposit).setTaskStatus(Status.NEW);
        }
        idsArrayList.clear();
        arrayListForCopy.clear();
    }
}
