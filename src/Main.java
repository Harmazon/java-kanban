import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Manager manager = new Manager();

        while (true) back:{
            showMenu();
            int command = in.nextInt();
            switch (command) {
                case 1:
                    System.out.println("Введи задачу");
                    String taskTitle = in.next();
                    System.out.println("Введи описание");
                    String taskDescription = in.next();
                    manager.putTask(new Task(taskTitle, taskDescription));
                    break;
                case 2:
                    System.out.println("Введи ID");
                    int setId = in.nextInt();
                    System.out.println(manager.showTask(setId));
                    break;
                case 3:
                    System.out.println("Простые задачи:");
                    System.out.println(manager.showAllSimpleTasks());
                    System.out.println("Эпики:");
                    System.out.println(manager.showAllEpics());
                    System.out.println("Прим.: чтобы проверить содержимое эпиков (его подзадачи)," +
                            " перейдите на стр. 2 и выберите п.2");
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
                    System.out.println("Введи id");
                    int idUpd = in.nextInt();
                    System.out.println("Введи задачу");
                    String taskTitleUpd = in.next();
                    System.out.println("Введи описание");
                    String taskDescriptionUpd = in.next();
                    manager.updateTask(idUpd, new Task(taskTitleUpd, taskDescriptionUpd));
                    break;
                case 7:
                    System.out.println("Введи id");
                    int idUpdDone = in.nextInt();
                    manager.changeStatusDone(idUpdDone);
                    break;
                case 8:
                    System.out.println("Введи id");
                    int idUpdInProg = in.nextInt();
                    manager.changeStatusInProgress(idUpdInProg);
                    break;
                case 9:
                    while (true) {
                        showMenuNextPage();
                        int command2 = in.nextInt();
                        switch (command2) {
                            case 1:
                                System.out.println("Введи id задачи, к которой нужно добавить подзадачу");
                                int idEpic = in.nextInt();
                                System.out.println("Введи подзадачу");
                                String subTask = in.next();
                                System.out.println("Введи описание подзадачи");
                                String subTaskDesc = in.next();
                                manager.putSubTask(idEpic, new Subtask(subTask, subTaskDesc));
                                break;
                            case 2:
                                System.out.println("Введи ID");
                                int idTestEpic = in.nextInt();
                                manager.changeAndCheckEpicStatus(idTestEpic);
                                System.out.println("Эпик");
                                System.out.println(manager.showTask(idTestEpic));
                                System.out.println("Подзадачи");
                                manager.showSubTasks(idTestEpic);
                                break;
                            case 3:
                                System.out.println("Введи id");
                                int idUpdDone2 = in.nextInt();
                                manager.changeStatusDone(idUpdDone2);
                                break;
                            case 4:
                                System.out.println("Введи id");
                                int idUpdInProg2 = in.nextInt();
                                manager.changeStatusInProgress(idUpdInProg2);
                                break;
                            case 9:
                                break back;
                            case 0:
                                return;
                        }
                    }
                case 0:
                    return;
            }
        }
    }

    static void showMenu() {
        System.out.println("Меню проверки функционала программы, стр. 1");
        System.out.println("1 - создать задачу");
        System.out.println("2 - показать задачу по ID");
        System.out.println("3 - показать все текущие задачи");
        System.out.println("4 - удалить задачу по ID");
        System.out.println("5 - удалить все задачи");
        System.out.println("6 - обновить задачу и её описание, статус изменится на IN_PROGRESS");
        System.out.println("7 - изменить статус задачи на DONE (*для проверки)");
        System.out.println("8 - изменить статус задачи на IN_PROGRESS (*для проверки)");
        System.out.println("9 - следующая страница");
        System.out.println("0 - выход");
    }

    static void showMenuNextPage() {
        System.out.println("Меню проверки функционала программы, стр. 2");
        System.out.println("1 - создать подзадачу и добавить к основной задаче");
        System.out.println("2 - показать эпик, его подзадачи и статус");
        System.out.println("3 - изменить статус задачи на DONE (*для проверки)");
        System.out.println("4 - изменить статус задачи на IN_PROGRESS (*для проверки)");
        System.out.println("9 - назад");
        System.out.println("0 - выход");
    }
}
