package service;

import model.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private int newId = 1;
    private int newCount = 0;
    private int doneCount = 0;
    private int inProgressCount = 0;
    protected final HashMap<Integer, Task> tasksHashMap = new HashMap<>();
    protected final HashMap<Integer, EpicTask> epicTasksHashMap = new HashMap<>();
    protected final HashMap<Integer, Subtask> subTasksHashMap = new HashMap<>();
    private final InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
    private final CustomList customList = new CustomList();
    List<Integer> listVault = new ArrayList<>();

    @Override
    public Task createTask(Task task) {                                         // создание и return простой задачи
        task.setTaskId(newId);
        newId++;
        tasksHashMap.put(task.getTaskId(), task);
        return task;
    }

    @Override
    public EpicTask createEpic(EpicTask epicTask) {                              // создание и return эпика
        epicTask.setTaskId(newId);
        newId++;
        epicTasksHashMap.put(epicTask.getTaskId(), epicTask);
        return epicTask;
    }

    @Override
    public Subtask createSubTask(Subtask subtask) {                             // создание и return подзадачи
        subtask.setTaskId(newId);
        subTasksHashMap.put(subtask.getTaskId(), subtask);
        if (epicTasksHashMap.containsKey(subtask.getEpicId())) {
            ArrayList arrayList = new ArrayList(epicTasksHashMap.get(subtask.getEpicId()).getSubTasksOfEpic());
            epicTasksHashMap.get(subtask.getEpicId()).getSubTasksOfEpic().clear();
            arrayList.add(subtask.getTaskId());
            epicTasksHashMap.get(subtask.getEpicId()).setSubTasksOfEpic(arrayList);
            newId++;
            checkAndChangeStatus(epicTasksHashMap.get(subtask.getEpicId()));
            return subtask;
        } else {
            subTasksHashMap.remove(subtask.getTaskId());
            return null;
        }
    }

    @Override
    public Task getTask(int id) {                               // вывести в консоль задачу, эпик или подзадачу
        if (tasksHashMap.containsKey(id)) {
            inMemoryHistoryManager.add(tasksHashMap.get(id));

            if (!(listVault.contains(id))) {
                listVault.add(id);
                customList.add(tasksHashMap.get(id));
            } else {
                customList.remove(tasksHashMap.get(id));
                customList.add(tasksHashMap.get(id));
            }

            return tasksHashMap.get(id);                        // return задача
        } else if (epicTasksHashMap.containsKey(id)) {
            inMemoryHistoryManager.add(epicTasksHashMap.get(id));

            if (!(listVault.contains(id))) {
                listVault.add(id);
                customList.add(epicTasksHashMap.get(id));
            } else {
                customList.remove(epicTasksHashMap.get(id));
                customList.add(epicTasksHashMap.get(id));
            }

            return epicTasksHashMap.get(id);                    // return эпик
        } else if (subTasksHashMap.containsKey(id)) {
            inMemoryHistoryManager.add(subTasksHashMap.get(id));

            if (!(listVault.contains(id))) {
                listVault.add(id);
                customList.add(subTasksHashMap.get(id));
            } else {
                customList.remove(subTasksHashMap.get(id));
                customList.add(subTasksHashMap.get(id));
            }

            return subTasksHashMap.get(id);                     // return подзадача
        } else {
            System.out.println("Такого id нет");
            return null;
        }
    }

    public void showCustomList() {
        customList.print();
    }

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
        for (int key : epicTasksHashMap.get(id).getSubTasksOfEpic()) {
            subTasksArr.add(subTasksHashMap.get(key));
        }
        return subTasksArr;
    }

    @Override
    public void removeTask(int id) {
        tasksHashMap.remove(id);
    }

    @Override
    public void removeEpic(int id) {
        for (int key : epicTasksHashMap.get(id).getSubTasksOfEpic()) {      // удаляются подзадачи эпика
            subTasksHashMap.remove(key);
        }
        epicTasksHashMap.remove(id);                                       // удаляется эпик по id
    }

    @Override
    public void removeSubTask(int id) {
        int epicID = subTasksHashMap.get(id).getEpicId();                   // ID эпика, в котором лежит подзадача
        int index = epicTasksHashMap.get(epicID).getSubTasksOfEpic().indexOf(id);   // Индекс искомой подз. в листе
        epicTasksHashMap.get(epicID).getSubTasksOfEpic().remove(index);     // удаление подз. из листа по индексу
        subTasksHashMap.remove(id);                                         // удаление подз. из хэш мапы
        checkAndChangeStatus(epicTasksHashMap.get(epicID));
    }

    @Override
    public void removeAllTasks() {
        tasksHashMap.clear();
    }

    @Override
    public void removeAllEpics() {
        for (Integer key : epicTasksHashMap.keySet()) {
            epicTasksHashMap.get(key).getSubTasksOfEpic().clear();
        }
        epicTasksHashMap.clear();
        subTasksHashMap.clear();
    }

    @Override
    public void removeAllSubTasks() {
        subTasksHashMap.clear();
        for (Integer key : epicTasksHashMap.keySet()) {
            epicTasksHashMap.get(key).getSubTasksOfEpic().clear();
            checkAndChangeStatus(epicTasksHashMap.get(key));
        }
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
        checkAndChangeStatus(epicTasksHashMap.get(subtask.getEpicId()));
    }

    @Override
    public List<Task> getHistory() {
        return inMemoryHistoryManager.getHistory();
    }

    protected void checkAndChangeStatus(EpicTask epicTask) {
        if (epicTask.getSubTasksOfEpic().isEmpty()) {
            epicTask.setTaskStatus(Status.NEW);
        } else {
            for (int key : epicTask.getSubTasksOfEpic()) {
                if (subTasksHashMap.get(key).getTaskStatus().equals(Status.IN_PROGRESS)) {
                    inProgressCount++;
                    if (inProgressCount > 0) {
                        epicTask.setTaskStatus(Status.IN_PROGRESS);
                    }
                } else if (subTasksHashMap.get(key).getTaskStatus().equals(Status.DONE)) {
                    doneCount++;
                    if (doneCount == epicTask.getSubTasksOfEpic().size()) {
                        epicTask.setTaskStatus(Status.DONE);
                    } else if (doneCount < epicTask.getSubTasksOfEpic().size()
                            && doneCount > 0) {
                        epicTask.setTaskStatus(Status.IN_PROGRESS);
                    }
                } else if (subTasksHashMap.get(key).getTaskStatus().equals(Status.NEW)) {
                    newCount++;
                    if (newCount < epicTask.getSubTasksOfEpic().size() && newCount > 0) {
                        epicTask.setTaskStatus(Status.IN_PROGRESS);
                    }
                    if (newCount == epicTask.getSubTasksOfEpic().size()) {
                        epicTask.setTaskStatus(Status.NEW);
                    }
                }
            }
        }
        newCount = 0;
        inProgressCount = 0;
        doneCount = 0;
    }
}
