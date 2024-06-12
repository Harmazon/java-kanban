import model.EpicTask;
import model.Status;
import model.Subtask;
import model.Task;
import service.Manager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Manager manager = new Manager();

        testTask();

        while (true) {
            showMenu();
            int command = in.nextInt();
            switch (command) {
                case 1:
                    System.out.println("Введи задачу");
                    String taskTitle = in.next();
                    System.out.println("Введи описание");
                    String taskDescription = in.next();
                    manager.createTask(new Task(taskTitle, taskDescription));
                    break;
                case 2:
                    System.out.println("Введи ID");
                    int setId = in.nextInt();
                    System.out.println(manager.getTask(setId));
                    break;
                case 3:
                    manager.printAllTasks();
                    break;
                case 4:
                    System.out.println("Введи ID");
                    int setIdRem = in.nextInt();
                    manager.removeTask(setIdRem);
                    break;
                case 5:
                    manager.removeAllTasks();
                    break;
                case 6:
                    System.out.println("Введи эпик");
                    String epicTitle = in.next();
                    System.out.println("Введи описание");
                    String epicDescription = in.next();
                    manager.createEpic(new EpicTask(epicTitle, epicDescription));
                    break;
                case 7:
                    System.out.println("Введи id эпика, к которому нужно добавить подзадачу");
                    int idEpic = in.nextInt();
                    System.out.println("Введи подзадачу");
                    String subTask = in.next();
                    System.out.println("Введи описание подзадачи");
                    String subTaskDesc = in.next();
                    manager.createSubTask(idEpic, new Subtask(subTask, subTaskDesc));
                    break;
                case 8:
                    System.out.println("Введи ID");
                    int idTestEpic = in.nextInt();
                    manager.changeAndCheckEpicStatus(idTestEpic);
                    System.out.println("Эпик");
                    System.out.println(manager.getTask(idTestEpic));
                    System.out.println("Подзадачи");
                    manager.printSubTasksOfEpic(idTestEpic);
                    break;
                case 0:
                    return;
            }
        }
    }

    static void showMenu() {
        System.out.println("\nМеню проверки функционала программы, стр. 1");
        System.out.println("1 - создать задачу");
        System.out.println("2 - показать задачу по ID");
        System.out.println("3 - показать все текущие задачи");
        System.out.println("4 - удалить задачу по ID");
        System.out.println("5 - удалить все задачи");
        System.out.println("6 - создать эпик");
        System.out.println("7 - создать подзадачу и добавить к основной задаче");
        System.out.println("8 - показать эпик, его подзадачи и статус");
        System.out.println("0 - выход");
    }

    static void testTask() {
        Manager manager = new Manager();
        Task task1 = new Task("Задача", "Описание");
        manager.createTask(task1);
        System.out.println(task1);
        task1.setTaskTitle("Новая задача");
        task1.setTaskDescription("Новое описание");
        task1.setTaskStatus(Status.DONE);
        manager.updateTask(task1);
        System.out.println(task1 + "\n\n");

        EpicTask epic = new EpicTask("Эпик", "Описание");
        Subtask subtask1 = new Subtask("Подзадача1", "Описание");
        Subtask subtask2 = new Subtask("Подзадача2", "Описание");
        Subtask subtask3 = new Subtask("Подзадача3", "Описание");

        manager.createEpic(epic);
        manager.createSubTask(epic.getTaskId(), subtask1);
        manager.createSubTask(epic.getTaskId(), subtask2);
        manager.createSubTask(epic.getTaskId(), subtask3);
        manager.printAllTasks();
        manager.removeTask(4);
        System.out.println("\nУдалил одну из подзадач");
        manager.printAllTasks();
        manager.removeTask(2);
        System.out.println("\nУдалил эпик");
        manager.printAllTasks();
    }
}
