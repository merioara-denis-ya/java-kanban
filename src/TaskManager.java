import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import types.Status;
import types.Task;
import types.Epic;
import types.Subtask;

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
    public Collection<Task> getTasks() {
        return tasks.values();
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
    public Collection<Epic> getEpics() {
        return epics.values();
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

        ArrayList<Subtask> subtasksByEpic = this.getSubtasksByEpicId(prevEpic.getId());
        for (Subtask subtask : subtasksByEpic) {
            Subtask subtaskWithEpic = new Subtask(subtask.getId(), subtask.getName(), subtask.getDescription(), subtask.getStatus(), prevEpic.getId());
            updateSubtask(subtaskWithEpic);
        }
    }

    /**
     * Удаление эпика
     */
    public void removeEpicById(Integer id) {
        epics.remove(id);
    }

    public ArrayList<Subtask> getSubtasksByEpicId(Integer epicId) {
        ArrayList<Subtask> result = new ArrayList<>();
        Epic epic = epics.get(epicId);

        if (epic == null) {
            return result;
        }

        for (int subtaskId : epic.getSubtaskIds()) {
            for (Subtask subtask : subtasks.values()) {
                if (subtask.getId() == subtaskId) {
                    result.add(subtask);
                }
            }
        }

        return result;
    }

    public void recalculateStatus(Integer epicId) {
        ArrayList<Subtask> subtaskList = this.getSubtasksByEpicId(epicId);
        Epic currentEpic = epics.get(epicId);
        Status currentStatus = currentEpic.getStatus();
        Status nextStatus;

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
        boolean hasProgressStatus = false;
        boolean hasDoneStatus = false;

        for (Subtask subtask : subtaskList) {
            if (subtask.getStatus() == Status.NEW) {
                hasNewStatus = true;
            }

            if (subtask.getStatus() == Status.IN_PROGRESS) {
                hasProgressStatus = true;
            }

            if (subtask.getStatus() == Status.DONE) {
                hasDoneStatus = true;
            }
        }

        if (hasNewStatus && !hasProgressStatus && !hasDoneStatus) {
            nextStatus = Status.NEW;
        } else if (!hasNewStatus && !hasProgressStatus && hasDoneStatus) {
            nextStatus = Status.DONE;
        } else {
            nextStatus = Status.IN_PROGRESS;
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
     * Получение списка всех задач
     */
    public Collection<Subtask> getSubtasks() {
        return subtasks.values();
    }

    /**
     * Удаление всех задач
     */
    public void removeAllSubtasks() {
        ArrayList<Integer> epicIdsTouched = new ArrayList<>();

        for (Subtask subtask : subtasks.values()) {
            Integer epicId = subtask.getEpicId();
            epicIdsTouched.add(epicId);
        }

        subtasks.clear();

        for (Integer epicId : epicIdsTouched) {
            this.recalculateStatus(epicId);
        }
    }

    /**
     * Получение задачи по идентификатору
     */
    public Subtask getSubtaskById(Integer id) {
        return subtasks.get(id);
    }

    /**
     * Создание задачи
     */
    public void createSubtask(Subtask item) {
        subtasks.put(item.getId(), item);
        this.linkSubtaskId2EpicById(item.getId(), item.getEpicId());
    }

    /**
     * Обновление задачи
     */
    public void updateSubtask(Subtask item) {
        subtasks.put(item.getId(), item);
        this.linkSubtaskId2EpicById(item.getId(), item.getEpicId());
        this.recalculateStatus(item.getEpicId());
    }

    /**
     * Обновление задачи
     */
    public void removeSubtaskById(Integer id) {
        Subtask subtask = getSubtaskById(id);
        if (subtask == null) {
            System.out.println("Нет подзадачи с идентификатором '" + id + "'");
            return;
        }

        Integer epicId = subtask.getEpicId();

        subtasks.remove(id);

        this.recalculateStatus(epicId);
    }

    protected boolean checkIfSubtasksLinkedWithEpic(ArrayList<Subtask> subtasks, int subtaskId) {
        return subtasks.stream().anyMatch((currentSubtask) -> currentSubtask.getId() == subtaskId);
    }

    /**
     * @param subtaskId Идентификатор подзадачи
     * @param epicId    Идентификатор Эпика
     */
    public void linkSubtaskId2EpicById(Integer subtaskId, Integer epicId) {
        Epic epic = epics.get(epicId);

        if (epic == null) {
            return;
        }


        ArrayList<Subtask> subtasks = this.getSubtasksByEpicId(epicId);
        boolean isSubtaskLinkedWithEpic = this.checkIfSubtasksLinkedWithEpic(subtasks, subtaskId);

        if (isSubtaskLinkedWithEpic) {
            return;
        }

        /* Region: удаление связей эпика с подзадачей */
        for (Epic epicItem : epics.values()) {
            if (epicItem.getId() == epicId) {
                continue;
            }

            ArrayList<Subtask> subtasksWithCurrentEpic = this.getSubtasksByEpicId(epicItem.getId());
            boolean isSubtaskLinkedWithCurrentEpic = this.checkIfSubtasksLinkedWithEpic(subtasksWithCurrentEpic, subtaskId);

            if (isSubtaskLinkedWithCurrentEpic) {
                ArrayList<Integer> subtaskIds = new ArrayList<>();

                for (Integer subtaskIdWithCurrentEpic : epicItem.getSubtaskIds()) {
                    if (!subtaskIdWithCurrentEpic.equals(subtaskId)) {
                        subtaskIds.add(subtaskIdWithCurrentEpic);
                    }
                }

                Epic nextEpic = new Epic(epicItem.getId(), epicItem.getName(), epicItem.getDescription(), epicItem.getStatus(), subtaskIds);
                updateEpic(nextEpic);
                this.recalculateStatus(nextEpic.getId());
            }
        }
        /* Region end */

        /* Region: добовление связи Эпика на подзадачу */
        epic.getSubtaskIds().add(subtaskId);
        /* Region end */
    }
    /* Region end */
}
