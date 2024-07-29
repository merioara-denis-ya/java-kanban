package com.yandex.app.service;

import com.yandex.app.model.Epic;
import com.yandex.app.model.Status;
import com.yandex.app.model.Subtask;
import com.yandex.app.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.UUID;

public class InMemoryTaskManagerTest {
    private TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = new InMemoryTaskManager();
    }

    @Test
    public void shouldInitialTasksCollectionIsEmpty() {
        List<Task> items = taskManager.getTasks();
        Assertions.assertTrue(items.isEmpty());
    }

    @Test
    public void shouldInitialEpicsCollectionIsEmpty() {
        List<Epic> items = taskManager.getEpics();
        Assertions.assertTrue(items.isEmpty());
    }

    @Test
    public void shouldInitialSubtaskCollectionIsEmpty() {
        List<Subtask> items = taskManager.getSubtasks();
        Assertions.assertTrue(items.isEmpty());
    }

    @Test
    public void shouldInitialHistoryCollectionIsEmpty() {
        List<? super Task> items = taskManager.getHistory();
        Assertions.assertTrue(items.isEmpty());
    }

    /* Region: Task */

    /**
     * Создание задачи
     * - создание задачи 'createTask'
     * - получение списка задач 'getTasks'
     * - получение задачи по идентификатору 'getTaskById'
     * - проверка целостности данных
     */
    @Test
    public void shouldCreateTask() {
        String name = UUID.randomUUID().toString();
        String description = UUID.randomUUID().toString();
        // Прверяем что колекция пустая
        Assertions.assertTrue(taskManager.getTasks().isEmpty());
        // Создаём задачу
        Integer id = taskManager.createTask(new Task(name, description));
        // Прверяем что колекция не пустая после создания задачи
        Assertions.assertEquals( 1, taskManager.getTasks().size());
        // Проверяем целостность данных
        Task item = taskManager.getTaskById(id);
        Assertions.assertEquals(id, item.getId());
        Assertions.assertEquals(name, item.getName());
        Assertions.assertEquals(description, item.getDescription());
        Assertions.assertEquals(Status.NEW, item.getStatus());
    }

    /**
     * Создание и удаление задач
     * - создание задачи 'createTask'
     * - получение списка задач 'getTasks'
     * - удаление задачи по идентификатору 'removeTaskById'
     * - удаление всех задач в коллекции 'removeAllTasks'
     */
    @Test
    public void shouldCreateAndRemoveMultipleTasks() {
        // Прверяем что колекция пустая
        Assertions.assertTrue(taskManager.getTasks().isEmpty());

        Integer id1 = taskManager.createTask(new Task(UUID.randomUUID().toString(), UUID.randomUUID().toString()));

        // Прверяем что количество задач в колекции 1
        Assertions.assertEquals(1, taskManager.getTasks().size());

        taskManager.createTask(new Task(UUID.randomUUID().toString(), UUID.randomUUID().toString()));

        // Прверяем что количество задач в колекции 1
        Assertions.assertEquals(2, taskManager.getTasks().size());

        taskManager.createTask(new Task(UUID.randomUUID().toString(), UUID.randomUUID().toString()));

        // Прверяем что количество задач в колекции 1
        Assertions.assertEquals(3, taskManager.getTasks().size());

        taskManager.removeTaskById(id1);

        // Прверяем что количество задач в колекции 1
        Assertions.assertEquals(2, taskManager.getTasks().size());
        // Прверяем что задача отсутсвует в колекции
        Assertions.assertNull(taskManager.getTaskById(id1));

        taskManager.removeAllTasks();

        // Прверяем что колекция пустая
        Assertions.assertTrue(taskManager.getTasks().isEmpty());
    }

    /** Обновление задачи */
    @Test
    public void shouldUpdateTask() {
        String initialName = UUID.randomUUID().toString();
        String initialDescription = UUID.randomUUID().toString();
        Integer id = taskManager.createTask(new Task(initialName, initialDescription));

        Task item = taskManager.getTaskById(id);

        Assertions.assertEquals(id, item.getId());
        Assertions.assertEquals(initialName, item.getName());
        Assertions.assertEquals(initialDescription, item.getDescription());
        Assertions.assertEquals(Status.NEW, item.getStatus());

        // Обновление поля 'name'
        String nextName = UUID.randomUUID().toString();
        taskManager.updateTask(new Task(id, nextName, item.getDescription(), item.getStatus()));
        item = taskManager.getTaskById(id);
        Assertions.assertEquals(nextName, item.getName());

        // Обновление поля 'description'
        String nextDescription = UUID.randomUUID().toString();
        taskManager.updateTask(new Task(id, item.getName(), nextDescription, item.getStatus()));
        item = taskManager.getTaskById(id);
        Assertions.assertEquals(nextDescription, item.getDescription());

        // Обновление поля 'status'
        Status nextStatus = Status.IN_PROGRESS;
        taskManager.updateTask(new Task(id, item.getName(), item.getDescription(), nextStatus));
        item = taskManager.getTaskById(id);
        Assertions.assertEquals(nextStatus, item.getStatus());
    }
    /* Region end */

    // ---------------------------------------

    /* Region: Subtask */

    /**
     * Создание подзадачи
     * - создание подзадачи 'createTask'
     * - получение списка подзадач 'getTasks'
     * - получение подзадачи по идентификатору 'getTaskById'
     * - проверка целостности данных
     */
    @Test
    public void shouldCreateSubtask() throws Exception {
        String name = UUID.randomUUID().toString();
        String description = UUID.randomUUID().toString();
        // Прверяем что колекция пустая
        Assertions.assertTrue(taskManager.getSubtasks().isEmpty());
        // Создаём подзадачу
        Integer epicId = taskManager.createEpic(new Epic(UUID.randomUUID().toString(), UUID.randomUUID().toString()));
        Integer subtaskId = taskManager.createSubtask(new Subtask(name, description, epicId));
        // Прверяем что колекция не пустая после создания подзадачи
        Assertions.assertEquals( 1, taskManager.getSubtasks().size());
        // Проверяем целостность данных
        Task item = taskManager.getSubtaskById(subtaskId);
        Assertions.assertEquals(subtaskId, item.getId());
        Assertions.assertEquals(name, item.getName());
        Assertions.assertEquals(description, item.getDescription());
        Assertions.assertEquals(Status.NEW, item.getStatus());
    }

    /**
     * Создание и удаление подзадач
     * - создание подзадачи 'createSubtask'
     * - получение списка подзадач 'getSubtasks'
     * - удаление подзадачи по идентификатору 'removeSubtaskById'
     * - удаление всех подзадач в коллекции 'removeAllSubtasks'
     */
    @Test
    public void shouldCreateAndRemoveMultipleSubtasks() throws Exception {
        Integer epicId = taskManager.createEpic(new Epic(UUID.randomUUID().toString(), UUID.randomUUID().toString()));

        // Прверяем что колекция пустая
        Assertions.assertTrue(taskManager.getSubtasks().isEmpty());

        Integer id1 = taskManager.createSubtask(new Subtask(UUID.randomUUID().toString(), UUID.randomUUID().toString(), epicId));

        // Прверяем что количество подзадач в колекции 1
        Assertions.assertEquals(1, taskManager.getSubtasks().size());

        taskManager.createSubtask(new Subtask(UUID.randomUUID().toString(), UUID.randomUUID().toString(), epicId));

        // Прверяем что количество подзадач в колекции 1
        Assertions.assertEquals(2, taskManager.getSubtasks().size());

        taskManager.createSubtask(new Subtask(UUID.randomUUID().toString(), UUID.randomUUID().toString(), epicId));

        // Прверяем что количество подзадач в колекции 1
        Assertions.assertEquals(3, taskManager.getSubtasks().size());

        taskManager.removeSubtaskById(id1);

        // Прверяем что количество подзадач в колекции 1
        Assertions.assertEquals(2, taskManager.getSubtasks().size());
        // Прверяем что подзадача отсутсвует в колекции
        Assertions.assertNull(taskManager.getSubtaskById(id1));

        taskManager.removeAllSubtasks();

        // Прверяем что колекция пустая
        Assertions.assertTrue(taskManager.getSubtasks().isEmpty());
    }

    /** Обновление подзадачи */
    @Test
    public void shouldUpdateSubtask() throws Exception {
        Integer epicId = taskManager.createEpic(new Epic(UUID.randomUUID().toString(), UUID.randomUUID().toString()));
        String initialName = UUID.randomUUID().toString();
        String initialDescription = UUID.randomUUID().toString();
        Integer subtaskId = taskManager.createSubtask(new Subtask(initialName, initialDescription, epicId));

        Task item = taskManager.getSubtaskById(subtaskId);

        Assertions.assertEquals(subtaskId, item.getId());
        Assertions.assertEquals(initialName, item.getName());
        Assertions.assertEquals(initialDescription, item.getDescription());
        Assertions.assertEquals(Status.NEW, item.getStatus());

        // Обновление поля 'name'
        String nextName = UUID.randomUUID().toString();
        taskManager.updateSubtask(new Subtask(subtaskId, nextName, item.getDescription(), item.getStatus(), epicId));
        item = taskManager.getSubtaskById(subtaskId);
        Assertions.assertEquals(nextName, item.getName());

        // Обновление поля 'description'
        String nextDescription = UUID.randomUUID().toString();
        taskManager.updateSubtask(new Subtask(subtaskId, item.getName(), nextDescription, item.getStatus(), epicId));
        item = taskManager.getSubtaskById(subtaskId);
        Assertions.assertEquals(nextDescription, item.getDescription());

        // Обновление поля 'status'
        Status nextStatus = Status.IN_PROGRESS;
        taskManager.updateSubtask(new Subtask(subtaskId, item.getName(), item.getDescription(), nextStatus, epicId));
        item = taskManager.getSubtaskById(subtaskId);
        Assertions.assertEquals(nextStatus, item.getStatus());
    }
    /* Region end */

    // ---------------------------------------

    /* Region: Epic */

    /**
     * Создание эписка
     * - создание эписка 'createTask'
     * - получение списка эписков 'getTasks'
     * - получение эписка по идентификатору 'getTaskById'
     * - проверка целостности данных
     */
    @Test
    public void shouldCreateEpic() {
        String name = UUID.randomUUID().toString();
        String description = UUID.randomUUID().toString();
        // Прверяем что колекция пустая
        Assertions.assertTrue(taskManager.getEpics().isEmpty());
        // Создаём эписк
        Integer id = taskManager.createEpic(new Epic(name, description));
        // Прверяем что колекция не пустая после создания эписка
        Assertions.assertEquals( 1, taskManager.getEpics().size());
        // Проверяем целостность данных
        Task item = taskManager.getEpicById(id);
        Assertions.assertEquals(id, item.getId());
        Assertions.assertEquals(name, item.getName());
        Assertions.assertEquals(description, item.getDescription());
        Assertions.assertEquals(Status.NEW, item.getStatus());
    }

    /**
     * Создание и удаление эписка
     * - создание эписка 'createEpic'
     * - получение списка эписков 'getEpics'
     * - удаление эписка по идентификатору 'removeEpicById'
     * - удаление всех эписков в коллекции 'removeAllEpics'
     */
    @Test
    public void shouldCreateAndRemoveMultipleEpics() {
        // Прверяем что колекция пустая
        Assertions.assertTrue(taskManager.getEpics().isEmpty());

        Integer id1 = taskManager.createEpic(new Epic(UUID.randomUUID().toString(), UUID.randomUUID().toString()));

        // Прверяем что количество эписков в колекции 1
        Assertions.assertEquals(1, taskManager.getEpics().size());

        taskManager.createEpic(new Epic(UUID.randomUUID().toString(), UUID.randomUUID().toString()));

        // Прверяем что количество эписков в колекции 2
        Assertions.assertEquals(2, taskManager.getEpics().size());

        taskManager.createEpic(new Epic(UUID.randomUUID().toString(), UUID.randomUUID().toString()));

        // Прверяем что количество эписков в колекции 3
        Assertions.assertEquals(3, taskManager.getEpics().size());

        taskManager.removeEpicById(id1);

        // Прверяем что количество эписков в колекции 2
        Assertions.assertEquals(2, taskManager.getEpics().size());
        // Прверяем что эписк отсутсвует в колекции
        Assertions.assertNull(taskManager.getSubtaskById(id1));

        taskManager.removeAllEpics();

        // Прверяем что колекция пустая
        Assertions.assertTrue(taskManager.getEpics().isEmpty());
    }

    /** Обновление эписка */
    @Test
    public void shouldUpdateEpic() {
        String initialName = UUID.randomUUID().toString();
        String initialDescription = UUID.randomUUID().toString();
        Integer id = taskManager.createEpic(new Epic(initialName, initialDescription));

        Task item = taskManager.getEpicById(id);

        Assertions.assertEquals(id, item.getId());
        Assertions.assertEquals(initialName, item.getName());
        Assertions.assertEquals(initialDescription, item.getDescription());
        Assertions.assertEquals(Status.NEW, item.getStatus());

        // Обновление поля 'name'
        String nextName = UUID.randomUUID().toString();
        taskManager.updateEpic(new Epic(id, nextName, item.getDescription(), item.getStatus()));
        item = taskManager.getEpicById(id);
        Assertions.assertEquals(nextName, item.getName());

        // Обновление поля 'description'
        String nextDescription = UUID.randomUUID().toString();
        taskManager.updateEpic(new Epic(id, item.getName(), nextDescription, item.getStatus()));
        item = taskManager.getEpicById(id);
        Assertions.assertEquals(nextDescription, item.getDescription());

        // Обновление поля 'status'
        Status currentStatus = item.getStatus();
        Status nextStatus = Status.IN_PROGRESS;
        taskManager.updateEpic(new Epic(id, item.getName(), item.getDescription(), nextStatus));
        item = taskManager.getEpicById(id);
        Assertions.assertEquals(currentStatus, item.getStatus());
    }
    /* Region end */
}
