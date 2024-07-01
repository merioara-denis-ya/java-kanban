import java.util.HashMap;

import types.Status;
import types.Task;
import types.Epic;
import types.Subtask;

public class TaskManager {
    private Integer index = 1;

    private final HashMap<Integer, Task> tasks = new HashMap<Integer, Task>();
    private final HashMap<Integer, Epic> epics = new HashMap<Integer, Epic>();

    public Task createTask(String name, String description) {
        Integer id = index++;
        Task item = new Task(id, name, description);
        tasks.put(id, item);
        return item;
    }

    public Epic createEpic(String name, String description) {
        Integer id = index++;
        Epic item = new Epic(id, name, description);
        epics.put(id, item);
        return item;
    }

    public Subtask createSubtask(String name, String description, Integer parentId) {
        Epic epic = epics.get(parentId);
        if (epic == null) {
            System.out.println("Эпик с идентификатором '" + parentId + "' не найти неудалось.");
            return null;
        }

        Integer id = index++;
        return new Subtask(id, name, description, epic);
    }

    /**  */
    public void printTaskList() {
        System.out.println("Список задач");
        for (Task task : tasks.values()) {
            System.out.println("> " + task);
        }
        System.out.println("Список эпиков");
        for (Epic epic : epics.values()) {
            System.out.println("> " + epic);
            System.out.println("  Список подзадач");
            for (Subtask subtask : epic.getSubtasks().values()) {
                System.out.println("  >> " + subtask);
            }
        }
    }

    public void updateTaskStatus(Integer id, Status status) {
        Task taskById = tasks.get(id);
        Epic epicById = epics.get(id);

        if (taskById != null) {
            Task next = taskById.setStatus(status);
            tasks.put(id, next);
        } else if (epicById != null) {
            epicById.setStatus(status);
            return;
        } else {
            boolean isExist = false;
            for (Epic epic : epics.values()) {
                Subtask subtask = epic.getSubtasks().get(id);
                if (subtask != null) {
                    isExist = true;
                    Subtask item = subtask.setStatus(status);
                    epic.getSubtasks().put(id, item);
                    Epic nextEpic = epic.recalculateStatus();
                    for (Subtask subtaskPrev : epic.getSubtasks().values()) {
                        new Subtask(subtaskPrev.getId(), subtaskPrev.getName(), subtaskPrev.getDescription(), subtaskPrev.getStatus(), nextEpic);
                    }
                    this.epics.put(nextEpic.getId(), nextEpic);
                }
            }

            if (isExist) return;
        }

        System.out.println("Задачу с идентификатором '" + id + "' не удалось найти.");
    }
}
