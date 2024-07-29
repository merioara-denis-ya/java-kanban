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
        Task task = taskManager.getTaskById(id);
        Assertions.assertEquals(id, task.getId());
        Assertions.assertEquals(name, task.getName());
        Assertions.assertEquals(description, task.getDescription());
        Assertions.assertEquals(Status.NEW, task.getStatus());
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

    @Test
    public void shouldUpdateTask() {
        String initialName = UUID.randomUUID().toString();
        String initialDescription = UUID.randomUUID().toString();
        Integer id = taskManager.createTask(new Task(initialName, initialDescription));

        Task task = taskManager.getTaskById(id);

        Assertions.assertEquals(id, task.getId());
        Assertions.assertEquals(initialName, task.getName());
        Assertions.assertEquals(initialDescription, task.getDescription());
        Assertions.assertEquals(Status.NEW, task.getStatus());

        // Обновление поля 'name'
        String nextName = UUID.randomUUID().toString();
        taskManager.updateTask(new Task(id, nextName, task.getDescription(), task.getStatus()));
        task = taskManager.getTaskById(id);
        Assertions.assertEquals(nextName, task.getName());

        // Обновление поля 'description'
        String nextDescription = UUID.randomUUID().toString();
        taskManager.updateTask(new Task(id, task.getName(), nextDescription, task.getStatus()));
        task = taskManager.getTaskById(id);
        Assertions.assertEquals(nextDescription, task.getDescription());

        // Обновление поля 'status'
        Status nextStatus = Status.IN_PROGRESS;
        taskManager.updateTask(new Task(id, task.getName(), task.getDescription(), nextStatus));
        task = taskManager.getTaskById(id);
        Assertions.assertEquals(nextStatus, task.getStatus());
    }
    /* Region end */
}
