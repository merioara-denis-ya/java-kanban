import types.*;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Task task1 = taskManager.createTask("Вынесть мусор", "Собрать мусор из кухни, туалета и ванной");
        Epic epic2 = taskManager.createEpic("Построить дачу", "Летная дача");
        Subtask subtask3 = taskManager.createSubtask("Найти участок", "минимум 1 гектар", epic2.getId());
        Subtask subtask4 = taskManager.createSubtask("Найти строительную компанию", "Уточнить рекомендаций у друзей и/или знакомых", epic2.getId());
        taskManager.printTaskList();
        System.out.println("-----------------------");
        taskManager.updateTaskStatus(task1.getId(), Status.IN_PROGRESS);
        taskManager.updateTaskStatus(subtask3.getId(), Status.IN_PROGRESS);
        taskManager.printTaskList();
        System.out.println("-----------------------");
        taskManager.updateTaskStatus(task1.getId(), Status.DONE);
        taskManager.updateTaskStatus(subtask3.getId(), Status.DONE);
        taskManager.updateTaskStatus(subtask4.getId(), Status.DONE);
        taskManager.printTaskList();
        System.out.println("-----------------------");
        taskManager.updateTaskStatus(epic2.getId(), Status.NEW);
    }
}
