package types;

import java.util.HashMap;

public class Epic extends Task {
    private final HashMap<Integer, Subtask> subtasks = new HashMap<Integer, Subtask>();

    public Epic(int id, String name, String description, Status status) {
        super(id, name, description, status);
    }

    public Epic(int id, String name, String description) {
        super(id, name, description);
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public Epic recalculateStatus() {
        Status nextStatus = this.getStatus();

        boolean isAllStatusNew = this.getSubtasks().values().isEmpty();
        boolean isAllStatusProgress = !this.getSubtasks().values().isEmpty();
        boolean isAllStatusDone = !this.getSubtasks().values().isEmpty();

        for (Subtask subtask : getSubtasks().values()) {
            if (isAllStatusNew && subtask.getStatus() != Status.NEW) {
                isAllStatusNew = false;
            }
            if (isAllStatusDone && subtask.getStatus() != Status.DONE) {
                isAllStatusDone = false;
            }
        }

        if (isAllStatusNew) {
            nextStatus = Status.NEW;
        } else if (isAllStatusDone) {
            nextStatus = Status.DONE;
        } else {
            nextStatus = Status.IN_PROGRESS;
        }

        return new Epic(this.getId(), this.getName(), this.getDescription(), nextStatus);
    }

    @Override
    public Epic setStatus(Status status) {
        System.out.println("Обновление статуса для Epic ограничено");
        return this;
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
