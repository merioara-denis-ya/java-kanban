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

    /**
     * Количество задач в истории
     */
    private final int lengthOfHistory;
    /**
     * Колекция последних просмотреных задач
     */
    private final List<? super Task> lastViewedTasks = new ArrayList<>();

    public InMemoryTaskManager() {
        lengthOfHistory = 10;
    }

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
        this.addingInHistory(item);
        return item;
    }

    @Override
    public Integer createTask(Task item) {
        Integer id = this.getIndex();
        Task instance = new Task(id, item.getName(), item.getDescription());
        tasks.put(instance.getId(), instance);
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
        this.addingInHistory(item);
        return item;
    }

    @Override
    public Integer createEpic(Epic item) {
        Integer id = this.getIndex();
        Epic instance = new Epic(id, item.getName(), item.getDescription());
        epics.put(id, instance);
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
    public ArrayList<Subtask> getSubtasksByEpicId(Integer epicId) {
        ArrayList<Subtask> result = new ArrayList<>();
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
        ArrayList<Subtask> subtaskList = this.getSubtasksByEpicId(epicId);
        Epic currentEpic = epics.get(epicId);
        Status currentStatus = currentEpic.getStatus();
        Status nextStatus = currentEpic.getStatus();

        if (subtaskList.isEmpty()) {
            nextStatus = Status.NEW;

            if (currentStatus == nextStatus) {
                return;
            }

            Epic epic = new Epic(currentEpic.getId(), currentEpic.getName(), currentEpic.getDescription(), nextStatus, currentEpic.getSubtaskIds());
            epics.put(epic.getId(), epic);
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
                    Epic epic = new Epic(currentEpic.getId(), currentEpic.getName(), currentEpic.getDescription(), Status.IN_PROGRESS, currentEpic.getSubtaskIds());

                    epics.put(epic.getId(), epic);
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

        Epic epic = new Epic(currentEpic.getId(), currentEpic.getName(), currentEpic.getDescription(), nextStatus, currentEpic.getSubtaskIds());

        epics.put(epic.getId(), epic);
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
        this.addingInHistory(item);
        return item;
    }

    /**
     * Создание подзадачи
     */
    @Override
    public Integer createSubtask(Subtask item) throws Exception {
        Integer id = this.getIndex();
        Subtask instance = new Subtask(id, item.getName(), item.getDescription(), item.getEpicId());
        subtasks.put(id, instance);
        this.linkSubtaskIdToEpicById(id, item.getEpicId());
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

        this.linkSubtaskIdToEpicById(item.getId(), item.getEpicId());
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

        this.unlinkSubtaskIdToEpicById(id, prevEpic.getId());
    }

    private <T extends Task> void addingInHistory(T item) {
        lastViewedTasks.add(item);

        while (lastViewedTasks.size() > this.lengthOfHistory) {
            lastViewedTasks.removeFirst();
        }
    }

    @Override
    public List<? super Task> getHistory() {
        return lastViewedTasks;
    }

    private boolean checkIfSubtaskIdsIncludesSubtaskId(List<Integer> subtasks, Integer subtaskId) {
        return subtasks.contains(subtaskId);
    }

    private void unlinkSubtaskIdToEpicById(Integer subtaskId, Integer epicId) {
        Epic epic = epics.get(epicId);

        if (epic == null) {
            return;
        }

        ArrayList<Integer> subtaskIds = new ArrayList<>(epic.getSubtaskIds());
        subtaskIds.remove(subtaskId);

        Epic nextEpic = new Epic(epic.getId(), epic.getName(), epic.getDescription(), epic.getStatus(), subtaskIds);
        epics.put(nextEpic.getId(), nextEpic);

        this.recalculateStatus(nextEpic.getId());
    }

    /**
     * @param subtaskId Идентификатор подзадачи
     * @param epicId    Идентификатор Эпика
     */
    private void linkSubtaskIdToEpicById(Integer subtaskId, Integer epicId) throws Exception {
        Epic epic = epics.get(epicId);

        if (epic == null) {
            throw new Exception("Exception message");
        }

        boolean isSubtaskLinkedWithEpic = this.checkIfSubtaskIdsIncludesSubtaskId(epic.getSubtaskIds(), subtaskId);

        if (isSubtaskLinkedWithEpic) {
            return;
        }

        /* Region: удаление связей эпика с подзадачей */
        Subtask subtask = subtasks.get(subtaskId);

        if (subtask != null) {
            this.unlinkSubtaskIdToEpicById(subtaskId, subtask.getEpicId());
        }
        /* Region end */

        // добовление связи Эпика на подзадачу
        epic.getSubtaskIds().add(subtaskId);
    }
    /* Region end */
}
