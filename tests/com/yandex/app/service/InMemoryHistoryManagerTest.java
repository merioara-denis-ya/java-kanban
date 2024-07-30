package com.yandex.app.service;

import com.yandex.app.model.Epic;
import com.yandex.app.model.Subtask;
import com.yandex.app.model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InMemoryHistoryManagerTest {
    public TaskManager taskManager;
    public HistoryManager historyManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = new InMemoryTaskManager();
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    public void shouldAddingTaskAndReadHistory() {
        Task task = new Task(1, "task name", "task description");
        Epic epic = new Epic(2, "epic name", "epic description");
        Subtask subtask = new Subtask(3, "subtask name", "subtask description", 2);

        Assertions.assertFalse(historyManager.getHistory().contains(task));
        historyManager.add(task);
        Assertions.assertTrue(historyManager.getHistory().contains(task));

        Assertions.assertFalse(historyManager.getHistory().contains(epic));
        historyManager.add(epic);
        Assertions.assertTrue(historyManager.getHistory().contains(epic));

        Assertions.assertFalse(historyManager.getHistory().contains(subtask));
        historyManager.add(subtask);
        Assertions.assertTrue(historyManager.getHistory().contains(subtask));


    }
}
