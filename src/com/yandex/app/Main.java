package com.yandex.app;

import com.yandex.app.model.Epic;
import com.yandex.app.model.Status;
import com.yandex.app.model.Subtask;
import com.yandex.app.model.Task;
import com.yandex.app.service.InMemoryTaskManager;
import com.yandex.app.service.TaskManager;

public class Main {
    public static void main(String[] args) {
        TaskManager inMemoryTaskManager = new InMemoryTaskManager();

        System.out.println("-- Tasks ------------------");

        /* Region: Создание */
        Task task1 = new Task("Вынесть мусор", "Собрать мусор из кухни, туалета и ванной");
        Integer task1Id = inMemoryTaskManager.createTask(task1);
        System.out.println("Создана новая задача: " + inMemoryTaskManager.getTaskById(task1Id));
        /* Region end */

        /* Region: Обновление Задачи */
        task1 = inMemoryTaskManager.getTaskById(task1Id);
        task1 = new Task(task1.getId(), "name", task1.getDescription(), task1.getStatus());
        inMemoryTaskManager.updateTask(task1);
        System.out.println("Обновление имени: " + inMemoryTaskManager.getTaskById(task1Id));
        task1 = inMemoryTaskManager.getTaskById(task1Id);
        task1 = new Task(task1.getId(), task1.getName(), "description", task1.getStatus());
        inMemoryTaskManager.updateTask(task1);
        System.out.println("Обновление описания: " + inMemoryTaskManager.getTaskById(task1Id));
        task1 = inMemoryTaskManager.getTaskById(task1Id);
        task1 = new Task(task1.getId(), task1.getName(), task1.getDescription(), Status.IN_PROGRESS);
        inMemoryTaskManager.updateTask(task1);
        System.out.println("Обновление статуса: " + inMemoryTaskManager.getTaskById(task1Id));
        /* Region end */

        /* Region: Получение по идентификатору */
        Task task2 = new Task("Task name", "Task description");
        Integer task2Id = inMemoryTaskManager.createTask(task2);
        task2 = inMemoryTaskManager.getTaskById(task2Id);
        System.out.println("Создана новая задача: " + inMemoryTaskManager.getTaskById(task2Id));
        System.out.println("Получение по идентификатору '" + task2Id + "': " + inMemoryTaskManager.getTaskById(task2Id));
        /* Region end */

        /* Region: Получение списка всех задач */
        System.out.println("Получение списка всех сущностей: " + inMemoryTaskManager.getTasks());
        /* Region end */

        /* Region: даление по идентификатору */
        inMemoryTaskManager.removeTaskById(task2.getId());
        System.out.println("Получение списка всех сущностей после удаления по идентификатором '" + task2.getId() + "': " + inMemoryTaskManager.getTasks());
        /* Region end */

        /* Region: Удаление всех задач */
        inMemoryTaskManager.removeAllTasks();
        System.out.println("Получение списка всех сущностей после очистки коллекции : " + inMemoryTaskManager.getTasks());
        /* Region end */

        System.out.println("-- Epics ------------------");

        /* Region: Создание */
        Epic epic1 = new Epic("Построить дачу", "Летная дача");
        Integer epic1Id = inMemoryTaskManager.createEpic(epic1);
        System.out.println("Создан новый эпик: " + inMemoryTaskManager.getEpicById(epic1Id));
        /* Region end */

        /* Region: Обновление Эпика */
        epic1 = inMemoryTaskManager.getEpicById(epic1Id);
        epic1 = new Epic(epic1.getId(), "name", epic1.getDescription(), epic1.getStatus());
        inMemoryTaskManager.updateEpic(epic1);
        System.out.println("Обновление имени: " + inMemoryTaskManager.getEpicById(epic1Id));
        epic1 = inMemoryTaskManager.getEpicById(epic1Id);
        epic1 = new Epic(epic1.getId(), epic1.getName(), "description", epic1.getStatus());
        inMemoryTaskManager.updateEpic(epic1);
        System.out.println("Обновление описания: " + inMemoryTaskManager.getEpicById(epic1Id));
        epic1 = inMemoryTaskManager.getEpicById(epic1Id);
        epic1 = new Epic(epic1.getId(), epic1.getName(), epic1.getDescription(), Status.IN_PROGRESS);
        inMemoryTaskManager.updateEpic(epic1);
        System.out.println("Обновление статуса: " + inMemoryTaskManager.getEpicById(epic1Id));
        /* Region end */

        /* Region: Получение по идентификатору */
        Epic epic2 = new Epic( "Epic name", "Epic description");
        Integer epic2Id = inMemoryTaskManager.createEpic(epic2);
        System.out.println("Получение по идентификатору '" + epic2Id + "': " + inMemoryTaskManager.getEpicById(epic2Id));
        /* Region end */

        /* Region: Получение списка всех задач */
        System.out.println("Получение списка всех сущностей: " + inMemoryTaskManager.getEpics());
        /* Region end */

        /* Region: даление по идентификатору */
        inMemoryTaskManager.removeEpicById(epic2Id);
        System.out.println("Получение списка всех сущностей после удаления по идентификатором '" + epic2Id + "': " + inMemoryTaskManager.getEpics());
        /* Region end */

        /* Region: Удаление всех задач */
        inMemoryTaskManager.removeAllEpics();
        System.out.println("Получение списка всех сущностей после очистки коллекции : " + inMemoryTaskManager.getEpics());
        /* Region end */

        System.out.println("-- Subtasks ------------------");

        epic1Id = inMemoryTaskManager.createEpic(new Epic("Построить дачу", "Летная дача"));
        epic2Id = inMemoryTaskManager.createEpic(new Epic("Epic name", "Epic description"));
        System.out.println("Добавим эпики для отлаки: " + inMemoryTaskManager.getEpics());

        /* Region: Создание */
        Subtask subtask1 = new Subtask("Найти участок", "минимум 1 гектар", epic1Id);
        Integer subtask1Id = inMemoryTaskManager.createSubtask(subtask1);
        System.out.println("Создана новая подзадача: " + inMemoryTaskManager.getSubtaskById(subtask1Id));
        /* Region end */

        /* Region: Обновление Задачи */
        subtask1 = inMemoryTaskManager.getSubtaskById(subtask1Id);
        subtask1 = new Subtask(subtask1.getId(), "name", subtask1.getDescription(), subtask1.getStatus(), subtask1.getEpicId());
        inMemoryTaskManager.updateSubtask(subtask1);
        System.out.println("Обновление имени: " + inMemoryTaskManager.getSubtaskById(subtask1Id));
        subtask1 = inMemoryTaskManager.getSubtaskById(subtask1Id);
        subtask1 = new Subtask(subtask1.getId(), subtask1.getName(), "description", subtask1.getStatus(), subtask1.getEpicId());
        inMemoryTaskManager.updateSubtask(subtask1);
        System.out.println("Обновление описания: " + inMemoryTaskManager.getSubtaskById(subtask1Id));
        subtask1 = inMemoryTaskManager.getSubtaskById(subtask1Id);
        subtask1 = new Subtask(subtask1.getId(), subtask1.getName(), subtask1.getDescription(), Status.IN_PROGRESS, subtask1.getEpicId());
        inMemoryTaskManager.updateSubtask(subtask1);
        System.out.println("Обновление статуса: " + inMemoryTaskManager.getSubtaskById(subtask1Id));
        /* Region end */

        /* Region: Получение по идентификатору */
        Subtask subtask2 = new Subtask("Subtask name", "Subtask description", epic1Id);
        Integer subtask2Id = inMemoryTaskManager.createSubtask(subtask2);
        System.out.println("Получение по идентификатору '" + subtask2Id + "': " + inMemoryTaskManager.getSubtaskById(subtask2Id));
        /* Region end */

        /* Region: Проверка рассчета статуса Эпика */
        subtask1 = inMemoryTaskManager.getSubtaskById(subtask1Id);
        inMemoryTaskManager.updateSubtask(new Subtask(subtask1.getId(), subtask1.getName(), subtask1.getDescription(), Status.NEW, subtask1.getEpicId()));

        System.out.println("All subtasks: " + inMemoryTaskManager.getSubtasks());
        System.out.println("All epics: " + inMemoryTaskManager.getEpics());

        System.out.println(">>>>>>> Все подзадачи в статусе: " + Status.NEW);
        subtask1 = inMemoryTaskManager.getSubtaskById(subtask1Id);
        inMemoryTaskManager.updateSubtask(new Subtask(subtask1.getId(), subtask1.getName(), subtask1.getDescription(), Status.NEW, subtask1.getEpicId()));
        System.out.println("Получение подзадач эпика '" + epic1Id + "': " + inMemoryTaskManager.getSubtasksByEpicId(epic1Id));
        System.out.println("Состояние эпика: " + inMemoryTaskManager.getEpicById(epic1Id));
        System.out.println(">>>>>>> Часть подзадачь в статусе: " + Status.IN_PROGRESS);
        subtask1 = inMemoryTaskManager.getSubtaskById(subtask1Id);
        inMemoryTaskManager.updateSubtask(new Subtask(subtask1.getId(), subtask1.getName(), subtask1.getDescription(), Status.IN_PROGRESS, epic1Id));
        System.out.println("Получение подзадач эпика: " + inMemoryTaskManager.getSubtasksByEpicId(epic1Id));
        System.out.println("Состояние эпика: " + inMemoryTaskManager.getEpicById(epic1Id));

        System.out.println(">>>>>>> Проверка статуса при изменение эпика: -------------" );
        System.out.println("All subtasks (до): " + inMemoryTaskManager.getSubtasks());
        System.out.println("All epics (до): " + inMemoryTaskManager.getEpics());
        subtask1 = inMemoryTaskManager.getSubtaskById(subtask1Id);
        subtask1 = new Subtask(subtask1.getId(), subtask1.getName(), subtask1.getDescription(), Status.IN_PROGRESS, epic2Id);
        inMemoryTaskManager.updateSubtask(subtask1);
        System.out.println("All subtasks (после): " + inMemoryTaskManager.getSubtasks());
        System.out.println("All epics (после): " + inMemoryTaskManager.getEpics());
        subtask1 = inMemoryTaskManager.getSubtaskById(subtask1Id);
        subtask1 = new Subtask(subtask1.getId(), subtask1.getName(), subtask1.getDescription(), Status.IN_PROGRESS, epic1Id);
        inMemoryTaskManager.updateSubtask(subtask1);
        System.out.println("All subtasks (после2): " + inMemoryTaskManager.getSubtasks());
        System.out.println("All epics (после2): " + inMemoryTaskManager.getEpics());
        System.out.println("Получение подзадач эпика: " + inMemoryTaskManager.getSubtasksByEpicId(epic1Id));
        System.out.println("Состояние эпика: " + inMemoryTaskManager.getEpicById(epic1Id));


        System.out.println(">>>>>>> Все подзадачь в статусе: " + Status.DONE);
        subtask1 = inMemoryTaskManager.getSubtaskById(subtask1Id);
        inMemoryTaskManager.updateSubtask(new Subtask(subtask1.getId(), subtask1.getName(), subtask1.getDescription(), Status.DONE, subtask1.getEpicId()));
        subtask2 = inMemoryTaskManager.getSubtaskById(subtask2Id);
        inMemoryTaskManager.updateSubtask(new Subtask(subtask2.getId(), subtask2.getName(), subtask2.getDescription(), Status.DONE, subtask2.getEpicId()));
        System.out.println("Получение подзадач эпика: " + inMemoryTaskManager.getSubtasksByEpicId(epic1Id));
        System.out.println("Состояние эпика: " + inMemoryTaskManager.getEpicById(epic1Id));
        /* Region end */


        /* Region: Получение списка всех задач */
        System.out.println("Получение подзадач эпика: " + inMemoryTaskManager.getSubtasksByEpicId(epic1Id));
        /* Region end */

        /* Region: Получение списка всех задач */
        System.out.println("Получение списка всех сущностей: " + inMemoryTaskManager.getSubtasks());
        /* Region end */

        /* Region: даление по идентификатору */
        inMemoryTaskManager.removeSubtaskById(subtask2Id);
        System.out.println("Получение списка всех сущностей после удаления по идентификатором '" + subtask2Id + "': " + inMemoryTaskManager.getSubtasks());
        /* Region end */

        /* Region: Удаление всех задач */
        inMemoryTaskManager.removeAllSubtasks();
        System.out.println("Получение списка всех сущностей после очистки коллекции : " + inMemoryTaskManager.getSubtasks());
        /* Region end */
    }
}
