package com.yandex.app.service;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import com.yandex.app.model.Status;
import com.yandex.app.model.Task;
import com.yandex.app.model.Epic;
import com.yandex.app.model.Subtask;

public class InMemoryTaskManager implements TaskManager {
    private Integer index = 1;

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();

    /**
     * Запрос уникального идентификатора
     */
    private Integer getIndex() {
        return index++;
    }

    /* Region: Task */
    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    @Override
    public Task getTaskById(Integer id) {
        Task item = tasks.get(id);
        inMemoryHistoryManager.add(item);
        return item;
    }

    @Override
    public Integer createTask(Task item) {
        Integer id = this.getIndex();
        item.setId(id);
        tasks.put(id, item);
        return id;
    }

    @Override
    public void updateTask(Task item) {
        tasks.put(item.getId(), item);
    }

    @Override
    public void removeTaskById(Integer id) {
        tasks.remove(id);
    }
    /* Region end */

    /* Region: Epic */
    @Override
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void removeAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public Epic getEpicById(Integer id) {
        Epic item = epics.get(id);
        inMemoryHistoryManager.add(item);
        return item;
    }

    @Override
    public Integer createEpic(Epic item) {
        Integer id = this.getIndex();
        item.setId(id);
        epics.put(id, item);
        return id;
    }

    @Override
    public void updateEpic(Epic item) {
        Epic prevEpic = epics.get(item.getId());
        if (prevEpic == null) {
            System.out.println("Отсутсвует эпик с идентифиатором '" + item.getId() + "'");
            return;
        }

        if (prevEpic.getStatus() != item.getStatus()) {
            System.out.println("Обновление статуса для эпиков ограничено");
            return;
        }

        epics.put(item.getId(), item);
    }

    @Override
    public void removeEpicById(Integer id) {
        Epic epic = epics.remove(id);

        if (epic == null) {
            return;
        }

        for (Integer subtaskId : epic.getSubtaskIds()) {
            this.removeSubtaskById(subtaskId);
        }
    }

    @Override
    public List<Subtask> getSubtasksByEpicId(Integer epicId) {
        List<Subtask> result = new ArrayList<>();
        Epic epic = epics.get(epicId);

        if (epic == null) {
            return result;
        }

        for (int subtaskId : epic.getSubtaskIds()) {
            Subtask subtask = subtasks.get(subtaskId);

            if (subtask == null) {
                continue;
            }

            result.add(subtask);
        }

        return result;
    }

    private void recalculateStatus(Integer epicId) {
        List<Subtask> subtaskList = this.getSubtasksByEpicId(epicId);
        Epic currentEpic = epics.get(epicId);
        Status currentStatus = currentEpic.getStatus();
        Status nextStatus = currentEpic.getStatus();

        if (subtaskList.isEmpty()) {
            nextStatus = Status.NEW;

            if (currentStatus == nextStatus) {
                return;
            }

            currentEpic.setStatus(nextStatus);
            return;
        }

        boolean hasNewStatus = false;
        boolean hasDoneStatus = false;

        for (Subtask subtask : subtaskList) {
            if (subtask.getStatus() == Status.NEW) {
                hasNewStatus = true;
            } else if (subtask.getStatus() == Status.DONE) {
                hasDoneStatus = true;
            } else if (subtask.getStatus() == Status.IN_PROGRESS) {
                if (currentEpic.getStatus() != Status.IN_PROGRESS) {
                    currentEpic.setStatus(Status.IN_PROGRESS);
                }

                return;
            }
        }

        if (hasNewStatus && !hasDoneStatus) {
            nextStatus = Status.NEW;
        } else if (!hasNewStatus && hasDoneStatus) {
            nextStatus = Status.DONE;
        }

        if (currentStatus == nextStatus) {
            return;
        }

        currentEpic.setStatus(nextStatus);
    }
    /* Region end */

    /* Region: Subtask */

    /**
     * Получение списка всех подзадачи
     */
    @Override
    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    /**
     * Удаление всех подзадачи
     */
    @Override
    public void removeAllSubtasks() {
        subtasks.clear();

        for (Epic epic : epics.values()) {
            List<Integer> subtaskIds = epic.getSubtaskIds();
            if (subtaskIds != null && !subtaskIds.isEmpty()) {
                Epic nextEpic = new Epic(epic.getId(), epic.getName(), epic.getDescription(), Status.NEW);
                epics.put(nextEpic.getId(), nextEpic);
            }
        }
    }

    /**
     * Получение подзадачи по идентификатору
     */
    @Override
    public Subtask getSubtaskById(Integer id) {
        Subtask item = subtasks.get(id);
        inMemoryHistoryManager.add(item);
        return item;
    }

    /**
     * Создание подзадачи
     */
    @Override
    public Integer createSubtask(Subtask item) throws Exception {
        Integer id = this.getIndex();
        item.setId(id);
        subtasks.put(id, item);
        Epic epic = epics.get(item.getEpicId());

        if (epic == null) {
            throw new Exception("Epic not found by id: " + id);
        }

        epic.linkSubtaskById(id);

        this.recalculateStatus(epic.getId());

        return id;
    }

    /**
     * Обновление подзадачи
     */
    @Override
    public void updateSubtask(Subtask item) throws Exception {
        Subtask prevSubtask = subtasks.get(item.getId());
        subtasks.put(item.getId(), item);

        if (prevSubtask.getEpicId().equals(item.getEpicId())) {
            this.recalculateStatus(item.getEpicId());
            return;
        }

        Epic prevEpicParent = epics.get(prevSubtask.getEpicId());

        if (prevEpicParent == null) {
            throw new Exception("Epic not found by id: " + prevSubtask.getEpicId());
        }

        prevEpicParent.unlinkSubtaskById(prevSubtask.getId());

        Epic nextEpicParent = epics.get(item.getEpicId());

        if (nextEpicParent == null) {
            throw new Exception("Epic not found by id: " + item.getEpicId());
        }

        nextEpicParent.linkSubtaskById(item.getId());
        this.recalculateStatus(item.getEpicId());
        this.recalculateStatus(prevSubtask.getEpicId());
    }

    /**
     * Обновление подзадачи
     */
    @Override
    public void removeSubtaskById(Integer id) {
        Subtask subtask = subtasks.remove(id);

        if (subtask == null) {
            System.out.println("Нет подзадачи с идентификатором '" + id + "'");
            return;
        }

        Epic prevEpic = epics.get(subtask.getEpicId());

        if (prevEpic == null) {
            return;
        }

        prevEpic.unlinkSubtaskById(id);
    }

    @Override
    public List<? super Task> getHistory() {
        return inMemoryHistoryManager.getHistory();
    }
    /* Region end */
}
