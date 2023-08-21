public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        taskManager.addEpic(new Epic("Бег", "Подготовиться к беговым тренировкам"));
        taskManager.addSubtask(1, new Subtask("Расписание", "Составить расписание беговых тренировок"));

        taskManager.addEpic(new Epic("Построить дом", "План строительства дома"));
        taskManager.addSubtask(3, new Subtask("Выровнять участок", "Заказать трактор для выравнивания участка"));
        taskManager.addSubtask(3, new Subtask("Установить фундамент", "Вызвать бригаду по заливке фундамента"));

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getSubtasksByEpic(1));
        System.out.println(taskManager.getSubtasksByEpic(3));

        taskManager.updateSubtask(2, TaskStatus.DONE, new Subtask("Расписание", "Составить расписание беговых тренировок"));
        System.out.println(taskManager.getTask(1));

        taskManager.updateSubtask(3, TaskStatus.DONE, new Subtask("Выровнять участок", "Заказать трактор для выравнивания участка"));
        taskManager.updateSubtask(4, TaskStatus.IN_PROGRESS, new Subtask("Установить фундамент", "Вызвать бригаду по заливке фундамента"));
        taskManager.updateSubtask(5, TaskStatus.NEW, new Subtask("Установить септик", "Купить септик и заказать его установку"));
        System.out.println(taskManager.getTask(3));

        taskManager.removeTask(1);
        taskManager.removeTask(2);
    }
}
