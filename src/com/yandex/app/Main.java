package com.yandex.app;

import com.yandex.app.model.Epic;
import com.yandex.app.model.Status;
import com.yandex.app.model.Subtask;
import com.yandex.app.model.Task;
import com.yandex.app.service.TaskManager;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        System.out.println("-- Tasks ------------------");

        /* Region: Создание */
        Task task1 = new Task("Вынесть мусор", "Собрать мусор из кухни, туалета и ванной");
        Integer task1Id = taskManager.createTask(task1);
        System.out.println("Создана новая задача: " + taskManager.getTaskById(task1Id));
        /* Region end */

        /* Region: Обновление Задачи */
        task1 = taskManager.getTaskById(task1Id);
        task1 = new Task(task1.getId(), "name", task1.getDescription(), task1.getStatus());
        taskManager.updateTask(task1);
        System.out.println("Обновление имени: " + taskManager.getTaskById(task1Id));
        task1 = taskManager.getTaskById(task1Id);
        task1 = new Task(task1.getId(), task1.getName(), "description", task1.getStatus());
        taskManager.updateTask(task1);
        System.out.println("Обновление описания: " + taskManager.getTaskById(task1Id));
        task1 = taskManager.getTaskById(task1Id);
        task1 = new Task(task1.getId(), task1.getName(), task1.getDescription(), Status.IN_PROGRESS);
        taskManager.updateTask(task1);
        System.out.println("Обновление статуса: " + taskManager.getTaskById(task1Id));
        /* Region end */

        /* Region: Получение по идентификатору */
        Task task2 = new Task("Task name", "Task description");
        Integer task2Id = taskManager.createTask(task2);
        task2 = taskManager.getTaskById(task2Id);
        System.out.println("Создана новая задача: " + taskManager.getTaskById(task2Id));
        System.out.println("Получение по идентификатору '" + task2Id + "': " + taskManager.getTaskById(task2Id));
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
        Epic epic1 = new Epic("Построить дачу", "Летная дача");
        Integer epic1Id = taskManager.createEpic(epic1);
        System.out.println("Создан новый эпик: " + taskManager.getEpicById(epic1Id));
        /* Region end */

        /* Region: Обновление Эпика */
        epic1 = taskManager.getEpicById(epic1Id);
        epic1 = new Epic(epic1.getId(), "name", epic1.getDescription(), epic1.getStatus());
        taskManager.updateEpic(epic1);
        System.out.println("Обновление имени: " + taskManager.getEpicById(epic1Id));
        epic1 = taskManager.getEpicById(epic1Id);
        epic1 = new Epic(epic1.getId(), epic1.getName(), "description", epic1.getStatus());
        taskManager.updateEpic(epic1);
        System.out.println("Обновление описания: " + taskManager.getEpicById(epic1Id));
        epic1 = taskManager.getEpicById(epic1Id);
        epic1 = new Epic(epic1.getId(), epic1.getName(), epic1.getDescription(), Status.IN_PROGRESS);
        taskManager.updateEpic(epic1);
        System.out.println("Обновление статуса: " + taskManager.getEpicById(epic1Id));
        /* Region end */

        /* Region: Получение по идентификатору */
        Epic epic2 = new Epic( "Epic name", "Epic description");
        Integer epic2Id = taskManager.createEpic(epic2);
        System.out.println("Получение по идентификатору '" + epic2Id + "': " + taskManager.getEpicById(epic2Id));
        /* Region end */

        /* Region: Получение списка всех задач */
        System.out.println("Получение списка всех сущностей: " + taskManager.getEpics());
        /* Region end */

        /* Region: даление по идентификатору */
        taskManager.removeEpicById(epic2Id);
        System.out.println("Получение списка всех сущностей после удаления по идентификатором '" + epic2Id + "': " + taskManager.getEpics());
        /* Region end */

        /* Region: Удаление всех задач */
        taskManager.removeAllEpics();
        System.out.println("Получение списка всех сущностей после очистки коллекции : " + taskManager.getEpics());
        /* Region end */

        System.out.println("-- Subtasks ------------------");

        epic1Id = taskManager.createEpic(new Epic("Построить дачу", "Летная дача"));
        epic2Id = taskManager.createEpic(new Epic("Epic name", "Epic description"));
        System.out.println("Добавим эпики для отлаки: " + taskManager.getEpics());

        /* Region: Создание */
        Subtask subtask1 = new Subtask("Найти участок", "минимум 1 гектар", epic1Id);
        Integer subtask1Id = taskManager.createSubtask(subtask1);
        System.out.println("Создана новая подзадача: " + taskManager.getSubtaskById(subtask1Id));
        /* Region end */

        /* Region: Обновление Задачи */
        subtask1 = taskManager.getSubtaskById(subtask1Id);
        subtask1 = new Subtask(subtask1.getId(), "name", subtask1.getDescription(), subtask1.getStatus(), subtask1.getEpicId());
        taskManager.updateSubtask(subtask1);
        System.out.println("Обновление имени: " + taskManager.getSubtaskById(subtask1Id));
        subtask1 = taskManager.getSubtaskById(subtask1Id);
        subtask1 = new Subtask(subtask1.getId(), subtask1.getName(), "description", subtask1.getStatus(), subtask1.getEpicId());
        taskManager.updateSubtask(subtask1);
        System.out.println("Обновление описания: " + taskManager.getSubtaskById(subtask1Id));
        subtask1 = taskManager.getSubtaskById(subtask1Id);
        subtask1 = new Subtask(subtask1.getId(), subtask1.getName(), subtask1.getDescription(), Status.IN_PROGRESS, subtask1.getEpicId());
        taskManager.updateSubtask(subtask1);
        System.out.println("Обновление статуса: " + taskManager.getSubtaskById(subtask1Id));
        /* Region end */

        /* Region: Получение по идентификатору */
        Subtask subtask2 = new Subtask("Subtask name", "Subtask description", epic1Id);
        Integer subtask2Id = taskManager.createSubtask(subtask2);
        System.out.println("Получение по идентификатору '" + subtask2Id + "': " + taskManager.getSubtaskById(subtask2Id));
        /* Region end */

        /* Region: Проверка рассчета статуса Эпика */
        subtask1 = taskManager.getSubtaskById(subtask1Id);
        taskManager.updateSubtask(new Subtask(subtask1.getId(), subtask1.getName(), subtask1.getDescription(), Status.NEW, subtask1.getEpicId()));

        System.out.println("All subtasks: " + taskManager.getSubtasks());
        System.out.println("All epics: " + taskManager.getEpics());

        System.out.println(">>>>>>> Все подзадачи в статусе: " + Status.NEW);
        subtask1 = taskManager.getSubtaskById(subtask1Id);
        taskManager.updateSubtask(new Subtask(subtask1.getId(), subtask1.getName(), subtask1.getDescription(), Status.NEW, subtask1.getEpicId()));
        System.out.println("Получение подзадач эпика '" + epic1Id + "': " + taskManager.getSubtasksByEpicId(epic1Id));
        System.out.println("Состояние эпика: " + taskManager.getEpicById(epic1Id));
        System.out.println(">>>>>>> Часть подзадачь в статусе: " + Status.IN_PROGRESS);
        subtask1 = taskManager.getSubtaskById(subtask1Id);
        taskManager.updateSubtask(new Subtask(subtask1.getId(), subtask1.getName(), subtask1.getDescription(), Status.IN_PROGRESS, epic1Id));
        System.out.println("Получение подзадач эпика: " + taskManager.getSubtasksByEpicId(epic1Id));
        System.out.println("Состояние эпика: " + taskManager.getEpicById(epic1Id));

        System.out.println(">>>>>>> Проверка статуса при изменение эпика: -------------" );
        System.out.println("All subtasks (до): " + taskManager.getSubtasks());
        System.out.println("All epics (до): " + taskManager.getEpics());
        subtask1 = taskManager.getSubtaskById(subtask1Id);
        subtask1 = new Subtask(subtask1.getId(), subtask1.getName(), subtask1.getDescription(), Status.IN_PROGRESS, epic2Id);
        taskManager.updateSubtask(subtask1);
        System.out.println("All subtasks (после): " + taskManager.getSubtasks());
        System.out.println("All epics (после): " + taskManager.getEpics());
        subtask1 = taskManager.getSubtaskById(subtask1Id);
        subtask1 = new Subtask(subtask1.getId(), subtask1.getName(), subtask1.getDescription(), Status.IN_PROGRESS, epic1Id);
        taskManager.updateSubtask(subtask1);
        System.out.println("All subtasks (после2): " + taskManager.getSubtasks());
        System.out.println("All epics (после2): " + taskManager.getEpics());
        System.out.println("Получение подзадач эпика: " + taskManager.getSubtasksByEpicId(epic1Id));
        System.out.println("Состояние эпика: " + taskManager.getEpicById(epic1Id));


        System.out.println(">>>>>>> Все подзадачь в статусе: " + Status.DONE);
        subtask1 = taskManager.getSubtaskById(subtask1Id);
        taskManager.updateSubtask(new Subtask(subtask1.getId(), subtask1.getName(), subtask1.getDescription(), Status.DONE, subtask1.getEpicId()));
        subtask2 = taskManager.getSubtaskById(subtask2Id);
        taskManager.updateSubtask(new Subtask(subtask2.getId(), subtask2.getName(), subtask2.getDescription(), Status.DONE, subtask2.getEpicId()));
        System.out.println("Получение подзадач эпика: " + taskManager.getSubtasksByEpicId(epic1Id));
        System.out.println("Состояние эпика: " + taskManager.getEpicById(epic1Id));
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
