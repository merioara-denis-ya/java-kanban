import types.*;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        System.out.println("-- Tasks ------------------");

        /* Region: Создание */
        Task task1 = new Task(taskManager.getIndex(), "Вынесть мусор", "Собрать мусор из кухни, туалета и ванной");
        taskManager.createTask(task1);
        /* Region end */

        /* Region: Обновление Задачи */
        task1 = new Task(task1.getId(), task1.getName(), "description", task1.getStatus());
        taskManager.updateTask(task1);
        task1 = new Task(task1.getId(), "name", task1.getDescription(), task1.getStatus());
        taskManager.updateTask(task1);
        task1 = new Task(task1.getId(), task1.getName(), task1.getDescription(), Status.IN_PROGRESS);
        taskManager.updateTask(task1);
        /* Region end */

        /* Region: Получение по идентификатору */
        Task task2 = new Task(taskManager.getIndex(), "Task name", "Task description");
        taskManager.createTask(task2);
        System.out.println("Получение по идентификатору: " + taskManager.getTaskById(task2.getId()));
        /* Region end */

        /* Region: Получение списка всех задач */
        System.out.println("Получение списка всех сущностей: " + taskManager.getTasks());
        /* Region end */

        /* Region: даление по идентификатору */
        taskManager.removeTaskById(task2.getId());
        System.out.println("Получение списка всех сущностей после удаления по идентификатором '" + task2.getId() + "': " + taskManager.getTasks());
        /* Region end */

        /* Region: Удаление всех задач */
        taskManager.removeAllTasks();
        System.out.println("Получение списка всех сущностей после очистки коллекции : " + taskManager.getTasks());
        /* Region end */

        System.out.println("-- Epics ------------------");

        /* Region: Создание */
        Epic epic1 = new Epic(taskManager.getIndex(), "Построить дачу", "Летная дача");
        taskManager.createEpic(epic1);
        /* Region end */

        /* Region: Обновление Эпика */
        epic1 = new Epic(epic1.getId(), epic1.getName(), "description", epic1.getStatus());
        taskManager.updateEpic(epic1);
        epic1 = new Epic(epic1.getId(), "name", epic1.getDescription(), epic1.getStatus());
        taskManager.updateEpic(epic1);
        epic1 = new Epic(epic1.getId(), epic1.getName(), epic1.getDescription(), Status.IN_PROGRESS);
        taskManager.updateEpic(epic1);
        /* Region end */

        /* Region: Получение по идентификатору */
        Epic epic2 = new Epic(taskManager.getIndex(), "Epic name", "Epic description");
        taskManager.createEpic(epic2);
        System.out.println("Получение по идентификатору: " + taskManager.getEpicById(epic2.getId()));
        /* Region end */

        /* Region: Получение списка всех задач */
        System.out.println("Получение списка всех сущностей: " + taskManager.getEpics());
        /* Region end */

        /* Region: даление по идентификатору */
        taskManager.removeEpicById(epic2.getId());
        System.out.println("Получение списка всех сущностей после удаления по идентификатором '" + epic2.getId() + "': " + taskManager.getEpics());
        /* Region end */

        /* Region: Удаление всех задач */
        taskManager.removeAllEpics();
        System.out.println("Получение списка всех сущностей после очистки коллекции : " + taskManager.getEpics());
        /* Region end */

        System.out.println("-- Subtasks ------------------");

        Integer epic1Id = taskManager.getIndex();
        taskManager.createEpic(new Epic(epic1Id, "Построить дачу", "Летная дача"));
        epic1 = taskManager.getEpicById(epic1Id);
        taskManager.createEpic(new Epic(taskManager.getIndex(), "Epic name", "Epic description"));
        System.out.println("Добавим эпики для отлаки: " + taskManager.getEpics());

        /* Region: Создание */
        Integer subtask1Id = taskManager.getIndex();
        Subtask subtask1 = new Subtask(subtask1Id, "Найти участок", "минимум 1 гектар", epic1);
        taskManager.createSubtask(subtask1);
        /* Region end */

        /* Region: Обновление Задачи */
        subtask1 = taskManager.getSubtaskById(subtask1Id);
        subtask1 = new Subtask(subtask1.getId(), subtask1.getName(), "description", subtask1.getStatus(), subtask1.getEpic());
        taskManager.updateSubtask(subtask1);
        subtask1 = taskManager.getSubtaskById(subtask1Id);
        subtask1 = new Subtask(subtask1.getId(), "name", subtask1.getDescription(), subtask1.getStatus(), subtask1.getEpic());
        taskManager.updateSubtask(subtask1);
        subtask1 = taskManager.getSubtaskById(subtask1Id);
        subtask1 = new Subtask(subtask1.getId(), subtask1.getName(), subtask1.getDescription(), Status.IN_PROGRESS, subtask1.getEpic());
        taskManager.updateSubtask(subtask1);
        /* Region end */

        /* Region: Получение по идентификатору */
        Integer subtask2Id = taskManager.getIndex();
        taskManager.createSubtask(new Subtask(subtask2Id, "Subtask name", "Subtask description", epic1));
        Subtask subtask2 = taskManager.getSubtaskById(subtask2Id);
        System.out.println("Получение по идентификатору: " + subtask2);
        /* Region end */

        /* Region: Проверка рассчета статуса Эпика */
        subtask1 = taskManager.getSubtaskById(subtask1Id);
        taskManager.updateSubtask(new Subtask(subtask1.getId(), subtask1.getName(), subtask1.getDescription(), Status.NEW, subtask1.getEpic()));

        System.out.println(">>>>>>> Все подзадачи в статусе: " + Status.NEW);
        subtask1 = taskManager.getSubtaskById(subtask1Id);
        taskManager.updateSubtask(new Subtask(subtask1.getId(), subtask1.getName(), subtask1.getDescription(), Status.NEW, subtask1.getEpic()));
        System.out.println("Получение подзадач эпика: " + taskManager.getSubtasksByEpicId(epic1.getId()));
        System.out.println("Состояние эпика: " + epic1);
        System.out.println(">>>>>>> Часть подзадачь в статусе: " + Status.IN_PROGRESS);
        subtask1 = taskManager.getSubtaskById(subtask1Id);
        subtask1 = new Subtask(subtask1.getId(), subtask1.getName(), subtask1.getDescription(), Status.IN_PROGRESS, subtask1.getEpic());
        taskManager.updateSubtask(subtask1);
        System.out.println("Получение подзадач эпика: " + taskManager.getSubtasksByEpicId(epic1.getId()));
        System.out.println("Состояние эпика: " + epic1);
        System.out.println(">>>>>>> Все подзадачь в статусе: " + Status.DONE);
        subtask1 = taskManager.getSubtaskById(subtask1Id);
        taskManager.updateSubtask(new Subtask(subtask1.getId(), subtask1.getName(), subtask1.getDescription(), Status.DONE, subtask1.getEpic()));
        subtask2 = taskManager.getSubtaskById(subtask2Id);
        taskManager.updateSubtask(new Subtask(subtask2.getId(), subtask2.getName(), subtask2.getDescription(), Status.DONE, subtask2.getEpic()));
        System.out.println("Получение подзадач эпика: " + taskManager.getSubtasksByEpicId(epic1.getId()));
        System.out.println("Состояние эпика: " + epic1);
        /* Region end */


        /* Region: Получение списка всех задач */
        System.out.println("Получение подзадач эпика: " + taskManager.getSubtasksByEpicId(epic1Id));
        /* Region end */

        /* Region: Получение списка всех задач */
        System.out.println("Получение списка всех сущностей: " + taskManager.getSubtasks());
        /* Region end */

        /* Region: даление по идентификатору */
        taskManager.removeSubtaskById(subtask2Id);
        System.out.println("Получение списка всех сущностей после удаления по идентификатором '" + subtask2Id + "': " + taskManager.getSubtasks());
        /* Region end */

        /* Region: Удаление всех задач */
        taskManager.removeAllSubtasks();
        System.out.println("Получение списка всех сущностей после очистки коллекции : " + taskManager.getSubtasks());
        /* Region end */
    }
}
