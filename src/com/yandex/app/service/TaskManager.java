package com.yandex.app.service;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import com.yandex.app.model.Status;
import com.yandex.app.model.Task;
import com.yandex.app.model.Epic;
import com.yandex.app.model.Subtask;

public class TaskManager {
    private Integer index = 1;

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();

    /**
     * Запрос уникального идентификатора
     */
    public Integer getIndex() {
        return index++;
    }

    /* Region: Task */

    /**
     * Получение списка всех задач
     */
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    /**
     * Удаление всех задач
     */
    public void removeAllTasks() {
        tasks.clear();
    }

    /**
     * Получение задачи по идентификатору
     */
    public Task getTaskById(Integer id) {
        return tasks.get(id);
    }

    /**
     * Создание задачи
     */
    public void createTask(Task item) {
        tasks.put(item.getId(), item);
    }

    /**
     * Обновление задачи
     */
    public void updateTask(Task item) {
        tasks.put(item.getId(), item);
    }

    /**
     * Удаление задачи
     */
    public void removeTaskById(Integer id) {
        tasks.remove(id);
    }
    /* Region end */

    /* Region: Epic */

    /**
     * Получение списка всех эпиков'
     */
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    /**
     * Удаление всех эпиков
     */
    public void removeAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    /**
     * Получение эпика по идентификатору
     */
    public Epic getEpicById(Integer id) {
        return epics.get(id);
    }

    /**
     * Создание эпика
     */
    public void createEpic(Epic item) {
        epics.put(item.getId(), item);
    }

    /**
     * Обновление эпика
     */
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

    /**
     * Удаление эпика
     */
    public void removeEpicById(Integer id) {
        Epic epic = epics.get(id);

        if (epic == null) {
            return;
        }

        epics.remove(id);
        for(Integer subtaskId : epic.getSubtaskIds()) {
            this.removeSubtaskById(subtaskId);
        }
    }

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

    public void recalculateStatus(Integer epicId) {
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
            }

            if (subtask.getStatus() == Status.IN_PROGRESS) {
                if (currentEpic.getStatus() != Status.IN_PROGRESS) {
                    Epic epic = new Epic(currentEpic.getId(), currentEpic.getName(), currentEpic.getDescription(), Status.IN_PROGRESS, currentEpic.getSubtaskIds());

                    epics.put(epic.getId(), epic);
                }

                return;
            }

            if (subtask.getStatus() == Status.DONE) {
                hasDoneStatus = true;
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
    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    /**
     * Удаление всех подзадачи
     */
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
    public Subtask getSubtaskById(Integer id) {
        return subtasks.get(id);
    }

    /**
     * Создание подзадачи
     */
    public void createSubtask(Subtask item) {
        subtasks.put(item.getId(), item);
        this.linkSubtaskIdToEpicById(item.getId(), item.getEpicId());
    }

    /**
     * Обновление подзадачи
     */
    public void updateSubtask(Subtask item) {
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

    protected boolean checkIfSubtaskIdsIncludesSubtaskId(ArrayList<Integer> subtasks, Integer subtaskId) {
        return subtasks.stream().anyMatch((currentSubtask) -> currentSubtask.equals(subtaskId));
    }

    public void unlinkSubtaskIdToEpicById(Integer subtaskId, Integer epicId) {
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
    public void linkSubtaskIdToEpicById(Integer subtaskId, Integer epicId) {
        Epic epic = epics.get(epicId);

        if (epic == null) {
            return;
        }


        boolean isSubtaskLinkedWithEpic = this.checkIfSubtaskIdsIncludesSubtaskId(epic.getSubtaskIds(), subtaskId);

        if (isSubtaskLinkedWithEpic) {
            return;
        }

        /* Region: удаление связей эпика с подзадачей */
        for (Epic epicItem : epics.values()) {
            if (epicItem.getId() == epicId) {
                continue;
            }

            boolean isSubtaskLinkedWithCurrentEpic = this.checkIfSubtaskIdsIncludesSubtaskId(epicItem.getSubtaskIds(), subtaskId);

            if (isSubtaskLinkedWithCurrentEpic) {
                this.unlinkSubtaskIdToEpicById(subtaskId, epicItem.getId());
            }
        }
        /* Region end */

        /* Region: добовление связи Эпика на подзадачу */
        epic.getSubtaskIds().add(subtaskId);
        /* Region end */
    }
    /* Region end */
}
