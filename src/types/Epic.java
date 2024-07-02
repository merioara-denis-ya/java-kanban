package types;

import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task {
    private final HashMap<Integer, Subtask> subtasks;

    public Epic(int id, String name, String description, Status status) {
        super(id, name, description, status);
        this.subtasks = new HashMap<>();
    }

    public Epic(int id, String name, String description, Status status, HashMap<Integer, Subtask> subtasks) {
        super(id, name, description, status);
        this.subtasks = subtasks;
    }

    public Epic(int id, String name, String description) {
        super(id, name, description);
        this.subtasks = new HashMap<>();
    }

    public ArrayList<Subtask> getSubtasks() {
        ArrayList<Subtask> result = new ArrayList<>();

        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpic() == null) {
                continue;
            }

            if (subtask.getEpic().equals(this)) {
                result.add(subtask);
            }
        }

        return result;
    }

    public void recalculateStatus() {
        ArrayList<Subtask> subtaskList = this.getSubtasks();

        if (subtaskList.isEmpty()) {
            this.status = Status.NEW;
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
            status = Status.NEW;
        } else if (!hasNewStatus && !hasProgressStatus && hasDoneStatus) {
            status = Status.DONE;
        } else {
            status = Status.IN_PROGRESS;
        }
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + this.getId() +
                ", name='" + this.getName() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                ", status=" + this.getStatus() +
                '}';
    }
}
