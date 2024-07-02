import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import types.Task;
import types.Epic;
import types.Subtask;

public class TaskManager {
    private Integer index = 1;

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();

    /** Запрос уникального идентификатора */
    public Integer getIndex() {
        return index++;
    }

    /* Region: Task */
    /** Получение списка всех задач */
    public Collection<Task> getTasks() {
         return tasks.values();
    }

    /** Удаление всех задач */
    public void removeAllTasks() {
        tasks.clear();
    }

    /** Получение задачи по идентификатору */
    public Task getTaskById(Integer id) {
        return tasks.get(id);
    }

    /** Создание задачи */
    public void createTask(Task item) {
        tasks.put(item.getId(), item);
    }

    /** Обновление задачи */
    public void updateTask(Task item) {
        tasks.put(item.getId(), item);
    }

    /** Удаление задачи */
    public void removeTaskById(Integer id) {
        tasks.remove(id);
    }
    /* Region end */

    /* Region: Epic */
    /** Получение списка всех эпиков' */
    public Collection<Epic> getEpics() {
        return epics.values();
    }

    /** Удаление всех эпиков */
    public void removeAllEpics() {
        epics.clear();
    }

    /** Получение эпика по идентификатору */
    public Epic getEpicById(Integer id) {
        return epics.get(id);
    }

    /** Создание эпика */
    public void createEpic(Epic item) {
        Epic itemWithSubtasks = new Epic(item.getId(), item.getName(), item.getDescription(), item.getStatus(), this.subtasks);
        epics.put(itemWithSubtasks.getId(), itemWithSubtasks);
    }

    /** Обновление эпика */
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

        Epic itemWithSubtasks = new Epic(item.getId(), item.getName(), item.getDescription(), item.getStatus(), this.subtasks);
        epics.put(itemWithSubtasks.getId(), itemWithSubtasks);

        for(Subtask subtask : prevEpic.getSubtasks()) {
            Subtask SubtaskWithEpic = new Subtask(subtask.getId(), subtask.getName(), subtask.getDescription(), subtask.getStatus(), itemWithSubtasks);
            updateSubtask(SubtaskWithEpic);
        }
    }

    /** Удаление эпика */
    public void removeEpicById(Integer id) {
        epics.remove(id);
    }

    public ArrayList<Subtask> getSubtasksByEpicId(Integer epicId) {
        ArrayList<Subtask> result = new ArrayList<>();
        Epic epic = epics.get(epicId);

        if (epic == null) {
            return result;
        }

        return epic.getSubtasks();
    }
    /* Region end */

    /* Region: Subtask */
    /** Получение списка всех задач */
    public Collection<Subtask> getSubtasks() {
        return subtasks.values();
    }

    /** Удаление всех задач */
    public void removeAllSubtasks() {
        HashMap<Integer, Epic> epicsTouched = new HashMap<>();

        for (Subtask subtask : subtasks.values()) {
            Epic epic = subtask.getEpic();
            epicsTouched.put(epic.getId(), epic);
        }

        subtasks.clear();

        for (Epic epic : epicsTouched.values()) {
            epic.recalculateStatus();
        }
    }

    /** Получение задачи по идентификатору */
    public Subtask getSubtaskById(Integer id) {
        return subtasks.get(id);
    }

    /** Создание задачи */
    public void createSubtask(Subtask item) {
        subtasks.put(item.getId(), item);
    }

    /** Обновление задачи */
    public void updateSubtask(Subtask item) {
        subtasks.put(item.getId(), item);

        item.getEpic().recalculateStatus();
    }

    /** Обновление задачи */
    public void removeSubtaskById(Integer id) {
        Subtask subtask = getSubtaskById(id);
        if (subtask == null) {
            System.out.println("Нет подзадачи с идентификатором '" + id + "'");
            return;
        }

        Epic epic = subtask.getEpic();

        subtasks.remove(id);

        epic.recalculateStatus();
    }
    /* Region end */
}
